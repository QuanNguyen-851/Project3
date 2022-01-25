package com.example.project3.model.dto;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.model.entity.ProductInformationEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Transient;
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
  private String avatarUrl;
  private List<ProductInformationEntity> listInformation;
  private List<ImageEntity> listImage;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
}
