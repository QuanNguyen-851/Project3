package com.example.project3.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaleDTO {
  private Long id;
  private Long productId;
  private Float sale;
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
}
