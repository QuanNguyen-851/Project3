package com.example.project3.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductInformation {
  private Long productId;
  private String key;
  private String value;
}
