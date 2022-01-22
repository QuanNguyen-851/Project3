package com.example.project3.Common;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;

public class Maper {
  private static final Maper INSTANCE = new Maper();

  // Private constructor to avoid client applications to use constructor
  private Maper() {

  }

  public static Maper getInstance() {
    return INSTANCE;
  }
  public ProductDTO ProductEntityToDTO(ProductEntity productEntity) {
    return ProductDTO.builder()
        .id(productEntity.getId())
        .code(productEntity.getCode())
        .name(productEntity.getName())
        .description(productEntity.getDescription())
        .categoryId(productEntity.getCategoryId())
        .productionId(productEntity.getProductionId())
        .salePrice(productEntity.getSalePrice())
        .importPrice(productEntity.getImportPrice())
        .discount(productEntity.getDiscount())
        .quantity(productEntity.getQuantity())
        .status(productEntity.getStatus())
        .build();
  }
}
