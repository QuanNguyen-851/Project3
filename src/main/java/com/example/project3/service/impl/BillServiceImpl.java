package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.Common.Maper;
import com.example.project3.Common.Token;
import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.BillDetailResponse;
import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import com.example.project3.model.entity.BillEntity.BillTypeEnum;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.ProductEntity.ProductEnum;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.model.entity.TurnoverEntity;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.ImportProductRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.repository.BillDetailRepository;
import com.example.project3.repository.ShoppingCartRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillService;
import com.example.project3.service.ProdSoldService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Override
  public List<BillDTO> getAll(Long profileId, String phone, String status, String type, Date startDate, Date endDate) {

    LocalDateTime end = LocalDateTime.now();
    LocalDateTime start = end.minusMonths(5);
    if (startDate != null) {
      start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    if (endDate != null) {
      end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    List<BillDTO> res = new ArrayList<>();
    for (BillEntity bill : repository.getAll(profileId, phone, status, type, start, end)) {
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
        detailres.setProductCode(product.getCode());
        detailres.setProductName(product.getName());
        if (product.getAvatarUrl() != null) {
          detailres.setProductAvatar(product.getAvatarUrl());
        }
        detailResponses.add(detailres);
      }
      BillDTO billDTO = Maper.getInstance().BillEntityToBillDTO(bill);
      billDTO.setBillDetail(detailResponses);
      billDTO.setCreateBy(profileRepository.findFirstById(billDTO.getProfileId()));
      return billDTO;
    }
    return null;
  }

  @Override
  public ResponseWrapper create(BillDTO billDTO) {
    var er = EnumResponse.FAIL;
//    if (billDTO.getId() != null) {
//      if (billDTO.getProfileId() == null) {
//        er.setResponseMessage("không có profile id");
//        return new ResponseWrapper(er, billDTO);
//      }
//    }
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
      var prod = productRepository.findFirstById(billDetail.getProductId());
      if (prod == null) {
        return new ResponseWrapper(EnumResponse.NOT_FOUND, billDetail, "sản phẩm này hiện không còn tồn tại trong hệ thống!");
      }else if(prod.getStatus().equals(ProductEnum.PAUSE.name())){
        return new ResponseWrapper(EnumResponse.FAIL, billDetail.getProductId(), "sản phẩm này hiện đã ngừng kinh doanh!");

      }
    }
    billDTO.setProfileId(Long.parseLong(token.sub("id")));
    billDTO.setCreatedDate(LocalDateTime.now());
    billDTO.setModifiedDate(LocalDateTime.now());
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
//    if (billDTO.getId() != null) {
//      if (billDTO.getProfileId() == null) {
//        er.setResponseMessage("không có profile id");
//        return new ResponseWrapper(er, billDTO);
//      }
//    }
    if (billDTO.getBillDetail().isEmpty() || billDTO.getBillDetail() == null) {
      er.setResponseMessage("không có sản phẩm nào trong giỏ hàng à? ");
      return new ResponseWrapper(er, billDTO);
    }
    var products = billDTO.getBillDetail();
    for (BillDetailResponse billDetail : products) {
      var pr = productRepository.findFirstById(billDetail.getProductId());
      if(pr.getStatus().equals(ProductEnum.PAUSE.name())){
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
//        //lưu lại số lượng sản phẩm đã bán
//        prodSoldService.saveProdSold(billDetail.getProductId(), billDetail.getQuantity());
      }
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
  public ResponseWrapper updateStatus(Long billId, String status) {
    var bill = repository.findFirstById(billId);
    if (bill != null) {
      if (!bill.getStatus().equals(BillStatusEnum.COMPLETED.name()) && !bill.getStatus().equals(BillStatusEnum.CANCELED.name())) {
        List<BillDetailEntity> list = billDetailRepository.findAllByBillId(bill.getId());
        if(status.equals(BillStatusEnum.COMPLETED.name())){
          //lưu lại số lượng sản phẩm đã bán
          for (BillDetailEntity billDetail : list) {
            prodSoldService.saveProdSold(billDetail.getProductId(), billDetail.getQuantity());
          }
        }else if(status.equals(BillStatusEnum.CANCELED.name())){
          for (BillDetailEntity billDetail : list) {
            var prod = productRepository.findFirstById(billDetail.getProductId());
            Long newQuantity= prod.getQuantity() + billDetail.getQuantity();
            if(newQuantity>0){
              prod.setStatus(ProductEnum.ACTIVE.name());
            }
            prod.setQuantity(newQuantity);
            productRepository.save(prod);          }
        }
        bill.setStatus(status);
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
    var entity = Maper.getInstance().ToBillDetailEntity(detailResponse);
    var res = billDetailRepository.save(entity);
    return Maper.getInstance().DetailEntityToResponse(res);
  }
}
