package com.example.project3.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
//@NoArgsConstructor
public class ProductDTO {
  private Long id;
  private String code;
  private String name;
  private String subName;
  private String description;
  private Long price;
  private Long quantity;
  private String status;
  private String category;
  private String production;
}
