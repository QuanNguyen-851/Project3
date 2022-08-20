package com.example.project3.model.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VoucherDTO {
  private Long id;
  private String name;
  private String key;
  private Float percentage;
  private Long discountPrice;
  private Boolean isPercent;
  private LocalDate startDate;
  private LocalDate endDate;
  private Long minPrice;
  private Long quantity;

}
