package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.Common.Maper;
import com.example.project3.Common.Token;
import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.BillDetailResponse;
import com.example.project3.model.dto.NotificationRequest;
import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.BillDetailImeiEntity;
import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import com.example.project3.model.entity.BillEntity.BillTypeEnum;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.NotificationEntity.RedirectEnum;
import com.example.project3.model.entity.ProductEntity.ProductEnum;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.model.entity.TurnoverEntity;
import com.example.project3.repository.BillDetailImeiRepository;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.ImportProductRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.repository.BillDetailRepository;
import com.example.project3.repository.ShoppingCartRepository;
import com.example.project3.repository.VoucherRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillService;
import com.example.project3.service.NotificationService;
import com.example.project3.service.ProdSoldService;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class BillServiceImpl implements BillService {

  @Autowired
  private BillRepository repository;

  @Autowired
  private BillDetailRepository billDetailRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private ProdSoldService prodSoldService;
  @Autowired
  private ImportProductRepository importProductRepository;
  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private Token token;

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private BillDetailImeiRepository billDetailImeiRepository;

  @Autowired
  private BillDetailImeiServiceImpl billDetailImeiServiceImpl;

  @Autowired
  private VoucherRepository voucherRepository;
  @Override
  public List<BillDTO> getAll(Long profileId, String phone, String status, String type, Date startDate, Date endDate, String code, String imei) {

    LocalDateTime end = LocalDateTime.now();
    LocalDateTime start = end.minusMonths(5);
    if (startDate != null) {
      start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    if (endDate != null) {
      end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    List<BillDTO> res = new ArrayList<>();
    for (BillEntity bill : repository.getAll(profileId, phone, status, type, start, end, code, imei)) {
      var dto = Maper.getInstance().BillEntityToBillDTO(bill);
      dto.setCreateBy(profileRepository.findFirstById(dto.getProfileId()));
      res.add(dto);
    }
    return res;
  }

  @Override
  public BillDTO getById(Long billId) {
    var bill = repository.findFirstById(billId);
    List<BillDetailResponse> detailResponses = new ArrayList<>();
    if (bill != null) {
      List<BillDetailEntity> billdetails = billDetailRepository.findAllByBillId(bill.getId());
      for (BillDetailEntity detail : billdetails) {
        var product = productRepository.findFirstById(detail.getProductId());
        var detailres = Maper.getInstance().DetailEntityToResponse(detail);
        var imei = billDetailImeiRepository.findAllByBillDetailIdOrderByIdAsc(detailres.getId());
        detailres.setProductCode(product.getCode());
        detailres.setProductName(product.getName());
        detailres.setImei(imei.stream().map(entity -> entity.getImei()).collect(Collectors.toList()));
        if (product.getAvatarUrl() != null) {
          detailres.setProductAvatar(product.getAvatarUrl());
        }
        detailResponses.add(detailres);
      }
      ProfileEntity modifiedBy = new ProfileEntity();
      if (bill.getModifiedBy() != null) {
        modifiedBy = profileRepository.findFirstById(bill.getModifiedBy());
      }
      BillDTO billDTO = Maper.getInstance().BillEntityToBillDTO(bill);
      billDTO.setBillDetail(detailResponses);
      billDTO.setModifiedBy(modifiedBy);
      billDTO.setCreateBy(profileRepository.findFirstById(billDTO.getProfileId()));
      return billDTO;
    }
    return null;
  }

  @Override
  public ResponseWrapper create(BillDTO billDTO) {

    var er = EnumResponse.FAIL;
    if (billDTO.getBillDetail().isEmpty() || billDTO.getBillDetail() == null) {
      er.setResponseMessage("không có sản phẩm nào trong giỏ hàng à? ");
      return new ResponseWrapper(er, billDTO);
    }

    var profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
    if (!profile.getRole().equals(RoleEnum.USER.name())) {
      billDTO.setType(BillTypeEnum.OFFLINE.name());
      billDTO.setStatus(BillStatusEnum.COMPLETED.name());
    }
    for (BillDetailResponse billDetail : billDTO.getBillDetail()) {
      var isValidImei=  billDetailImeiServiceImpl.areAllUnique(billDetail.getImei());
      if(!isValidImei){
        return new ResponseWrapper(EnumResponse.FAIL,billDetail, "imei phải là duy nhất !");
      }
      var prod = productRepository.findFirstById(billDetail.getProductId());
      if (prod == null) {
        return new ResponseWrapper(EnumResponse.NOT_FOUND, billDetail, "sản phẩm này hiện không còn tồn tại trong hệ thống!");
      } else if (prod.getStatus().equals(ProductEnum.PAUSE.name())) {
        return new ResponseWrapper(EnumResponse.FAIL, billDetail.getProductId(), "sản phẩm này hiện đã ngừng kinh doanh!");

      }
    }
    if(billDTO.getVoucherId()!=null){
      var voucher = voucherRepository.findFirstById(billDTO.getVoucherId());
      if(voucher.getQuantity()<=1){
        return new ResponseWrapper(EnumResponse.FAIL, voucher.getKey(), "đã hết! ");
      }
      if(voucher.getMinPrice()>billDTO.getTotalPrice()){
        return new ResponseWrapper(EnumResponse.FAIL, voucher.getMinPrice(), String.format("voucher %s có giá trị đơn hàng tối thiểu là %s ", voucher.getKey(), voucher.getMinPrice()));
      }
      voucher.setQuantity(voucher.getQuantity()-1);
      voucher.setModifiedDate(LocalDateTime.now());
      voucherRepository.save(voucher);
    }
    billDTO.setProfileId(Long.parseLong(token.sub("id")));
    billDTO.setCreatedDate(LocalDateTime.now());
    billDTO.setModifiedDate(LocalDateTime.now());
    billDTO.setModifiedBy(ProfileEntity.builder().id(profile.getId()).build());
    billDTO.setCode(this.getSaltString());
    var billres = repository.save(Maper.getInstance().BillDTOToBillEntity(billDTO));
    List<BillDetailResponse> detailResponseList = new ArrayList<>();
    if (billres.getId() != null) {
      for (BillDetailResponse billDetail : billDTO.getBillDetail()) {
        billDetail.setBillId(billres.getId());
        var prod = productRepository.findFirstById(billDetail.getProductId());
        if (prod.getWarranty() != null && prod.getWarranty() > 0) {
          billDetail.setWarrantyEndDate(LocalDateTime.now().plusMonths(prod.getWarranty()));
        }
        detailResponseList.add(this.saveDetail(billDetail));
        //lưu lại số lượng sản phẩm đã bán
        prodSoldService.saveProdSold(billDetail.getProductId(), billDetail.getQuantity());
      }
    }

    var billResponse = Maper.getInstance().BillEntityToBillDTO(billres);
    billResponse.setBillDetail(detailResponseList);
    return new ResponseWrapper(EnumResponse.SUCCESS, billResponse);
  }

  @Override
  public ResponseWrapper createByUser(BillDTO billDTO) {
    var er = EnumResponse.FAIL;
    if (billDTO.getBillDetail().isEmpty() || billDTO.getBillDetail() == null) {
      er.setResponseMessage("không có sản phẩm nào trong giỏ hàng à? ");
      return new ResponseWrapper(er, billDTO);
    }
    var products = billDTO.getBillDetail();
    for (BillDetailResponse billDetail : products) {
      var isValidImei=  billDetailImeiServiceImpl.areAllUnique(billDetail.getImei());
      if(!isValidImei){
        return new ResponseWrapper(EnumResponse.FAIL,billDetail, "imei phải là duy nhất !");
      }
      var pr = productRepository.findFirstById(billDetail.getProductId());
      if (pr.getStatus().equals(ProductEnum.PAUSE.name())) {
        return new ResponseWrapper(EnumResponse.FAIL, billDetail.getProductId(),
            "sản phẩm này hiện đã ngừng kinh doanh!");
      }
      if (pr.getQuantity().equals(0L)) {
        return new ResponseWrapper(EnumResponse.FAIL, billDetail.getProductId(),
            String.format("Sản phẩm này trong kho đã hết !"));
      } else if (billDetail.getQuantity() > pr.getQuantity()) {
        return new ResponseWrapper(EnumResponse.FAIL, billDetail.getProductId(),
            String.format("Sản phẩm này trong kho chỉ còn %d ", pr.getQuantity()));
      }
    }

    var profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
    billDTO.setProfileId(profile.getId());
    billDTO.setType(billDTO.getType());
    if (billDTO.getType().equals(BillTypeEnum.COD.name())) {
      billDTO.setStatus(BillStatusEnum.VERIFYING.name());
    } else if (billDTO.getType().equals(BillTypeEnum.ONLINE.name())) {
      billDTO.setStatus(BillStatusEnum.VERIFIED.name());
    } else {
      billDTO.setStatus(BillStatusEnum.COMPLETED.name());
    }

    for (BillDetailResponse billDetail : billDTO.getBillDetail()) {
      var prod = productRepository.findFirstById(billDetail.getProductId());
      if (prod == null) {
        return new ResponseWrapper(EnumResponse.NOT_FOUND, billDetail, "sản phẩm này hiện không còn tồn tại trong hệ thống!");
      }
    }
    billDTO.setCreatedDate(LocalDateTime.now());
    billDTO.setModifiedDate(LocalDateTime.now());
    billDTO.setModifiedBy(ProfileEntity.builder().id(profile.getId()).build());
    billDTO.setCode(this.getSaltString());
    var billres = repository.save(Maper.getInstance().BillDTOToBillEntity(billDTO));
    clearShoppingCartByProduct(billDTO.getBillDetail());
    List<BillDetailResponse> detailResponseList = new ArrayList<>();
    if (billres.getId() != null) {
      for (BillDetailResponse billDetail : billDTO.getBillDetail()) {
        billDetail.setBillId(billres.getId());
        var prod = productRepository.findFirstById(billDetail.getProductId());
        if (prod.getWarranty() != null && prod.getWarranty() > 0) {
          billDetail.setWarrantyEndDate(LocalDateTime.now().plusMonths(prod.getWarranty()));
        }
        detailResponseList.add(this.saveDetail(billDetail));
//        uppdate lại số lượng sản phẩm trong ko
        Long newQuantity = prod.getQuantity() - billDetail.getQuantity();
        if (newQuantity <= 0L) {
          prod.setQuantity(0L);
          prod.setStatus(ProductEnum.EMPTY.name());
        } else {
          prod.setQuantity(newQuantity);
        }
        productRepository.save(prod);
      }
      //        //gửi thông báo tới toàn bộ adminUser
      List<ProfileEntity> moderators = profileRepository.getAllProfileByRoles(
          new ArrayList<>(List.of(RoleEnum.ADMIN, RoleEnum.EMPLOYEE, RoleEnum.SUPERADMIN)));

      JsonObject params = new JsonObject();
      params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
      params.addProperty("billId", billres.getId());
      notificationService.createNotification(NotificationRequest.builder()
          .senderId(profile.getId())
          .profileId(moderators.stream().map(ProfileEntity::getId).collect(Collectors.toList()))
          .body(String.format("%s đã tạo một đơn hàng mới! ", profile.getFistName() + " " + profile.getLastName()))
          .params(params)
          .title("Vừa có một đơn hàng mới !")
          .build());
    }
    var billResponse = Maper.getInstance().BillEntityToBillDTO(billres);
    billResponse.setBillDetail(detailResponseList);
    return new ResponseWrapper(EnumResponse.SUCCESS, billResponse);
  }

  private List<Long> clearShoppingCartByProduct(List<BillDetailResponse> products) {
    return products.stream()
        .map(BillDetailResponse::getProductId)
        .map(productId -> {
          var cartEntity = shoppingCartRepository.findFirstByProfileIdAndProductId(Long.parseLong(token.sub("id")), productId);
          if (cartEntity != null) {
            shoppingCartRepository.deleteById(cartEntity.getId());
            return productId;
          }
          return productId;
        })
        .collect(Collectors.toList());
  }

  @Override
  public ResponseWrapper updateStatus(Long billId, String status, String reason) {
    var requestProfile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
    if (Objects.isNull(requestProfile)) {
      return new ResponseWrapper(EnumResponse.NOT_FOUND, Long.parseLong(token.sub("id")), "Không thể tìm thấy user này!");
    }
    var bill = repository.findFirstById(billId);
    if (bill != null) {
      if (!bill.getStatus().equals(BillStatusEnum.COMPLETED.name()) && !bill.getStatus().equals(BillStatusEnum.CANCELED.name())) {
        List<BillDetailEntity> list = billDetailRepository.findAllByBillId(bill.getId());
        switch (BillStatusEnum.valueOf(status)) {
          case VERIFYING:
            if(BillStatusEnum.valueOf(bill.getStatus()).equals(BillStatusEnum.CANCELED_REQUEST)){
              List<ProfileEntity> moderators = profileRepository.getAllProfileByRoles(
                  new ArrayList<>(List.of(RoleEnum.ADMIN, RoleEnum.EMPLOYEE, RoleEnum.SUPERADMIN)));
              ProfileEntity profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(profile.getId())
                  .profileId(moderators.stream().map(ProfileEntity::getId).collect(Collectors.toList()))
                  .body(String.format("%s đã xóa yêu cầu hủy đơn hàng %s! ", profile.getFistName() + " " + profile.getLastName(),  bill.getCode()))
                  .params(params)
                  .title("Thông báo !")
                  .build());
            }else {
              return new ResponseWrapper(EnumResponse.FAIL, Long.parseLong(token.sub("id")),
                  "Đơn hàng này đang không ở trạng thái đang chờ duyệt yêu cầu hủy!");
            }
            break;
          case VERIFIED:
            //bắn thông báo cho user
            if (!bill.getProfileId().equals(Long.parseLong(token.sub("id")))) {
              //        //gửi thông báo tới user đặt hàng
              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(Long.parseLong(token.sub("id")))
                  .profileId(List.of(bill.getProfileId()))
                  .body(String.format("Đơn hàng %s đã được xác nhận!", bill.getCode()))
                  .params(params)
                  .title("Nhị Quân Store !")
                  .build());
            }
            break;
          case INPROGRESS:
            if (!bill.getProfileId().equals(Long.parseLong(token.sub("id")))) {
              //        //gửi thông báo tới user đặt hàng
              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(Long.parseLong(token.sub("id")))
                  .profileId(List.of(bill.getProfileId()))
                  .body(String.format("Đơn hàng %s đang được giao!",  bill.getCode()))
                  .params(params)
                  .title("Nhị Quân Store !")
                  .build());
            }
            break;
          case COMPLETED:
            //lưu lại số lượng sản phẩm đã bán
            for (BillDetailEntity billDetail : list) {
              prodSoldService.saveProdSold(billDetail.getProductId(), billDetail.getQuantity());
            }

            if(requestProfile.getRole().equals(RoleEnum.USER.name())){
              //        //gửi thông báo tới toàn bộ adminUser
              List<ProfileEntity> moderators = profileRepository.getAllProfileByRoles(
                  new ArrayList<>(List.of(RoleEnum.ADMIN, RoleEnum.EMPLOYEE, RoleEnum.SUPERADMIN)));

              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(requestProfile.getId())
                  .profileId(moderators.stream().map(ProfileEntity::getId).collect(Collectors.toList()))
                  .body(String.format("Đơn hàng %s đã được giao thành công ! ", bill.getCode()))
                  .params(params)
                  .title("Thông báo !")
                  .build());
            }else{
              //gửi thông báo tới user đặt hàng
              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(Long.parseLong(token.sub("id")))
                  .profileId(List.of(bill.getProfileId()))
                  .body(String.format("Đơn hàng %s đã được giao thành công!",  bill.getCode()))
                  .params(params)
                  .title("Nhị Quân Store !")
                  .build());
            }
            break;
          case CANCELED:
            for (BillDetailEntity billDetail : list) {
              var prod = productRepository.findFirstById(billDetail.getProductId());
              Long newQuantity = prod.getQuantity() + billDetail.getQuantity();
              if (newQuantity > 0) {
                prod.setStatus(ProductEnum.ACTIVE.name());
              }
              prod.setQuantity(newQuantity);
              productRepository.save(prod);
            }
            if (!bill.getProfileId().equals(Long.parseLong(token.sub("id")))) {
              //        gửi thông báo tới user đặt hàng
              JsonObject params = new JsonObject();
              params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
              params.addProperty("billId", bill.getId());
              notificationService.createNotification(NotificationRequest.builder()
                  .senderId(Long.parseLong(token.sub("id")))
                  .profileId(List.of(bill.getProfileId()))
                  .body(String.format("Đơn hàng %s đã bị hủy!",  bill.getCode()))
                  .params(params)
                  .title("Nhị Quân Store !")
                  .build());
            }
            break;
          case CANCELED_REQUEST:
            List<ProfileEntity> moderators = profileRepository.getAllProfileByRoles(
                new ArrayList<>(List.of(RoleEnum.ADMIN, RoleEnum.EMPLOYEE, RoleEnum.SUPERADMIN)));
            ProfileEntity profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
            JsonObject params = new JsonObject();
            params.addProperty("Redirect", RedirectEnum.DETAIL_PAGE.name());
            params.addProperty("billId", bill.getId());
            notificationService.createNotification(NotificationRequest.builder()
                .senderId(profile.getId())
                .profileId(moderators.stream().map(ProfileEntity::getId).collect(Collectors.toList()))
                .body(String.format("%s đã yêu cầu hủy đơn hàng %s! ", profile.getFistName() + " " + profile.getLastName(),  bill.getCode()))
                .params(params)
                .title("Vừa có một yêu cầu hủy đơn !")
                .build());
            break;
          default:
            break;
        }
        bill.setStatus(status);
        bill.setReason(reason);
        bill.setModifiedBy(requestProfile.getId());
        bill.setModifiedDate(LocalDateTime.now());
        return new ResponseWrapper(EnumResponse.SUCCESS, repository.save(bill));
      }
      return new ResponseWrapper(EnumResponse.FAIL, bill, "Không thể cập nhật trạng thái của hóa đơn!");
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }

  @Override
  public NewBillResponse countNewBill(String status, String type) {
    var thismonth = FormatDate.getThisMonth();
    Long count = repository.countByStatusAndMonth(status, type, thismonth);
    var entities = repository.getNewBill(status, type, thismonth);
    List<BillDTO> data = new ArrayList<>();
    for (BillEntity item : entities) {
      data.add(Maper.getInstance().BillEntityToBillDTO(item));
    }
    return NewBillResponse.builder()
        .count(count)
        .data(data)
        .build();
  }

  @Override
  public TurnoverEntity getTurnover(String status, String type, String month) {
    var thismonth = month == null ? FormatDate.getThisMonth() : month;
    Long turnover = 0L; // số tiền thu được từ bill
    Long importPrice = 0L; // số tiền bỏ ra
    var entities = repository.getNewBill(status, type, thismonth);
    for (BillEntity item : entities) {
      turnover += item.getTotalPrice();
    }
    var importProductResponseList = importProductRepository.getByProductIdAndDate(null, thismonth, null);
    for (ImportProductResponse importRes : importProductResponseList) {
      importPrice += importRes.getImportTotal();
    }
    return TurnoverEntity.builder()
        .turnover(turnover)
        .billCount(entities.size())
        .importPrice(importPrice)
        .interestRate(turnover - importPrice)
        .build();
  }

  @Override
  public List<TopEmployee> getTopEmployee(Long limit, String profileRole) {
    var thismonth = FormatDate.getThisMonth();
    var list = repository.getTopEmployee(limit, thismonth, profileRole);
    for (TopEmployee item : list) {
      var profile = profileRepository.findFirstById(item.getProfileId());
      if (profile != null) {
        item.setProfileEntity(profile);
      }
    }
    return list;
  }


  private BillDetailResponse saveDetail(BillDetailResponse detailResponse) {
    var profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
    var entity = Maper.getInstance().ToBillDetailEntity(detailResponse);
    var res = billDetailRepository.save(entity);
    if (Objects.nonNull(profile)&& !CollectionUtils.isEmpty(detailResponse.getImei())) {
      for (String imei:  detailResponse.getImei()) {
        billDetailImeiRepository.save(BillDetailImeiEntity.builder()
            .imei(imei)
            .billDetailId(res.getId())
            .createdBy(profile.getId())
            .createdDate(LocalDateTime.now())
            .modifiedBy(profile.getId())
            .modifiedDate(LocalDateTime.now())
            .build());
      }
    }
    return Maper.getInstance().DetailEntityToResponse(res);
  }

  private String getSaltString() {
    ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
    long millis = zdt.toInstant().toEpochMilli();
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 10) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr+millis;
  }
}
