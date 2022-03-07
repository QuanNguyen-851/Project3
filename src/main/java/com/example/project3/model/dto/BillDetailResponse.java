package com.example.project3.model.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillDetailResponse {
  private Long id;
  private Long billId;
  private Long productId;
  private String productCode;
  private String productName;
  private String productAvatar;
  private LocalDateTime warrantyEndDate;
  //  private Long salePrice;
  private Long quantity;
  private Long price;
}
