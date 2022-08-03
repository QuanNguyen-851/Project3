package com.example.project3.model.dto;

import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarrantyHistoryResponse {
  private Long id;
  private String imei;
  private ProductResponse product;
  private ProfileEntity profile;
  private String productCondition;
  private WarrantyHistoryStatus status;
  private Long surcharge;
  private LocalDateTime createdDate;
}
