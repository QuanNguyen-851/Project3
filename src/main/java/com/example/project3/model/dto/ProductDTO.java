package com.example.project3.model.dto;

import com.example.project3.model.entity.ProductInformationEntity;
import java.util.List;
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
  private String description;
  private Long categoryId;
  private Long productionId;
  private Long salePrice;
  private Long importPrice;
  private Float discount;
  private Long quantity;
  private String status;
  private String category;
  private String production;
  private List<ProductInformationEntity> listInformation;
}
