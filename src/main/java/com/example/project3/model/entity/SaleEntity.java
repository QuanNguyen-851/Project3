package com.example.project3.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "s_sale")
public class SaleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String key;
  private Float percentage;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

}
