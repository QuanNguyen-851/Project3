package com.example.project3.model.entity;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ps_product_sold")
public class ProductSoldResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
//  @Transient
  private String code;
//  @Transient
  private String productName;
//  @Transient
  private Long quantity;
//  @Transient
  private Long salePrice;
//  @Transient
  private String status;
//  @Transient
  private String avatarUrl;
  private Date date;
  private Long sold;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
}
