package com.example.project3.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
@Entity
@Table(name = "p_product")
public class ProductEntity {

 public enum ProductEnum{
    ACTIVE,
    PAUSE,
    EMPTY
  }

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
  private String avatarUrl;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;


}
