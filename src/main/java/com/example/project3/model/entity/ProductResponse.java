package com.example.project3.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_product")
public class ProductResponse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  @Transient
  private List<ProductInformationEntity> listInformation;
  @Transient
  private List<ImageEntity> listImage;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
  @Transient
  private String createdByName;
  @Transient
  private String modifiedByName;
}
