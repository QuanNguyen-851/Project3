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

  @Id
//  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
//  @Column(name = "code")
  private String code;
//  @Column(name = "name")
  private String name;
//  @Column(name = "description")
//  private String subName;
  private String description;
//  @Column(name = "category_id")
  private Long categoryId;
//  @Column(name = "production_id")
  private Long productionId;
//  @Column(name = "price")
  private Long price;
//  @Column(name = "quantity")
  private Long quantity;
//  @Column(name = "status")
  private String status;
//  @Column(name = "avartar_url")
  private String avatarUrl;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

//  @Transient
//  private String category;
//  @Transient
//  private String production;


}
