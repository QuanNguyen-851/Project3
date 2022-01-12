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
  private Long price;
  private Long quantity;
  private String status;
    private String category;
  private String production;
  private String avatarUrl;
  @Transient
  private List<ProductInformationEntity> listInformation;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
}
