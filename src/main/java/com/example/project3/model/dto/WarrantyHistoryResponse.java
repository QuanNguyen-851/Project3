package com.example.project3.model.dto;

import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarrantyHistoryResponse {
  private String imei;
  private ProductResponse product;
  private List<WarrantyHistoryDetail> data;
}
