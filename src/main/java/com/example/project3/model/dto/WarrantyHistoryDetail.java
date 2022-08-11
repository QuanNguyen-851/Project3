package com.example.project3.model.dto;

import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarrantyHistoryDetail {
  private Long id;
  private String productCondition;
  private WarrantyHistoryStatus status;
  private Long surcharge;
  private LocalDateTime createdDate;
  private Long createdBy;
  private String createByName;
}
