package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.Common.Maper;
import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.BillDetailResponse;
import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import com.example.project3.model.entity.BillEntity.BillTypeEnum;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.model.entity.TurnoverEntity;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.repository.BillDetailRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillService;
import com.example.project3.service.ProdSoldService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


  @Override
  public List<BillDTO> getAll(Long profileId, String phone, String status, String type, Date startDate, Date endDate) {

    LocalDateTime end = LocalDateTime.now();
    LocalDateTime start = end.minusMonths(5);
    System.out.println("1  :" + start + "|" + end);
    if (startDate != null) {
      start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    if (endDate != null) {
      end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    System.out.println("1  :" + start + "|" + end);
    List<BillDTO> res = new ArrayList<>();
    for (BillEntity bill : repository.getAll(profileId, phone, status, type, start, end)) {
      res.add(Maper.getInstance().BillEntityToBillDTO(bill));
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
      return billDTO;
    }
    return null;
  }

  @Override
  public ResponseWrapper create(BillDTO billDTO) {
    var er = EnumResponse.FAIL;
    if (billDTO.getId() != null) {
      if (billDTO.getProfileId() == null) {
        er.setResponseMessage("không có profile id");
        return new ResponseWrapper(er, billDTO);
      }
    }
    if (billDTO.getBillDetail().isEmpty() || billDTO.getBillDetail() == null) {
      er.setResponseMessage("không có sản phẩm nào trong giỏ hàng à? ");
      return new ResponseWrapper(er, billDTO);
    }

    var profile = profileRepository.findFirstById(billDTO.getProfileId());
    if (!profile.getRole().equals(RoleEnum.USER.name())) {
      billDTO.setType(BillTypeEnum.OFFLINE.name());
      billDTO.setStatus(BillStatusEnum.VERIFIED.name());
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
    List<BillDetailResponse> detailResponseList = new ArrayList<>();
    if (billres.getId() != null) {
      for (BillDetailResponse billDetail : billDTO.getBillDetail()) {
        billDetail.setBillId(billres.getId());
        detailResponseList.add(this.saveDetail(billDetail));
        prodSoldService.saveProdSold(billDetail.getProductId(), billDetail.getQuantity());
      }
    }
    var billResponse = Maper.getInstance().BillEntityToBillDTO(billres);
    billResponse.setBillDetail(detailResponseList);
    return new ResponseWrapper(EnumResponse.SUCCESS, billResponse);
  }

  @Override
  public ResponseWrapper updateStatus(Long profileId, String status) {
    var bill = repository.findFirstById(profileId);
    if (bill != null) {
      if (bill.getStatus().equals(BillStatusEnum.VERIFYING.name())) {
        bill.setStatus(status);
        bill.setModifiedDate(LocalDateTime.now());
        return new ResponseWrapper(EnumResponse.SUCCESS, repository.save(bill));
      }
      return new ResponseWrapper(EnumResponse.FAIL, bill, "tạm thời chỉ cập nhật được hóa đơn đang VERIFYING");
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
  public TurnoverEntity getTurnover(String status, String type) {
    var thismonth = FormatDate.getThisMonth();
    Long turnover = 0L;
    Long importPrice = 0L;
    var entities = repository.getNewBill(status, type, thismonth);
    var prod = productRepository.getNewProd(thismonth, null);
    for (BillEntity item : entities) {
      turnover += item.getTotalPrice();
    }
    for (ProductResponse item : prod) {
      importPrice += item.getImportPrice();
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
      if(profile!=null){
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
