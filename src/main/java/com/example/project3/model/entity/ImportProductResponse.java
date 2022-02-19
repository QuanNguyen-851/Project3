package com.example.project3.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ip_import_product")
public class ImportProductResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long productId;
  private Long importTotal;
  private Long importQuantity;
  private LocalDateTime createdDate;
  private Long createdBy;
  private String code;
  private String name;
  private String avatarUrl;
  private Long quantity;
  private String fistName;
  private String lastName;
//  p.code, p.name, p.avatar_url, p.quantity, pf.fist_name, pf.last_name
}
