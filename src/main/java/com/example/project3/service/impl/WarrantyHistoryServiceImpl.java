package com.example.project3.service.impl;

import com.example.project3.Common.Maper;
import com.example.project3.Common.Token;
import com.example.project3.model.dto.WarrantyHistoryDetail;
import com.example.project3.model.dto.WarrantyHistoryResponse;
import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.repository.WarrantyHistoryRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;
import com.example.project3.service.ProfileService;
import com.example.project3.service.WarrantyHistoryService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class WarrantyHistoryServiceImpl implements WarrantyHistoryService {

  @Autowired
  private Token token;

  @Autowired
  private WarrantyHistoryRepository warrantyHistoryRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProfileService profileService;

  @Autowired
  private ProfileRepository profileRepository;

  @Override
  public List<WarrantyHistoryResponse> getWarrantyHistory(String searchKey, WarrantyHistoryStatus status) {
    var imeis = warrantyHistoryRepository.getListImei(searchKey, status);
    var val = new ArrayList<WarrantyHistoryResponse>();
    for (String imei : imeis) {
      var list = warrantyHistoryRepository.getListHistoryEntity(searchKey, imei, status);

      val.add(WarrantyHistoryResponse.builder()
          .imei(imei)
          .product(productService.getDetail(list.get(0).getProductId()))
          .data(list.stream().map(item -> {
            var profile = profileService.getById(item.getCreatedBy());
            return WarrantyHistoryDetail
                .builder()
                .id(item.getId())
                .productCondition(item.getProductCondition())
                .status(WarrantyHistoryStatus.valueOf(item.getStatus()))
                .surcharge(item.getSurcharge())
                .createdDate(item.getCreatedDate())
                .createdBy(item.getCreatedBy())
                .createByName(profile.getFistName() + " " + profile.getLastName())
                .build();
          }).collect(Collectors.toList()))
          .build());
    }
    return val;
  }

  @Override
  public ResponseWrapper createWarrantyHistory(WarrantyHistoryEntity entity) {
    Long myId = Long.parseLong(token.sub("id"));
    if (Objects.isNull(myId)) {
      return new ResponseWrapper(EnumResponse.NOT_FOUND, myId, "không tìm thấy người dùng này!");
    }
    var profile = profileService.getById(myId);
    if (Objects.isNull(profile)) {
      return new ResponseWrapper(EnumResponse.NOT_FOUND, myId, "không tìm thấy người dùng này!");
    }
    if (Objects.isNull(entity.getImei()) || Objects.isNull(entity.getProductId())) {
      return new ResponseWrapper(EnumResponse.FAIL, null, String.format("imei hoặc productId không dược để trống "));
    }
    entity.setCreatedBy(myId);
    entity.setModifiedBy(myId);
    entity.setCreatedDate(LocalDateTime.now());
    entity.setModifiedDate(LocalDateTime.now());
    return new ResponseWrapper(EnumResponse.SUCCESS, warrantyHistoryRepository.save(entity));
  }
}
