package com.example.project3.model.entity;

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
@Table(name = "p_product")
public class Producttest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  //  private String code;
  private String name;
  //  private String subName;
  private String description;
  private String avatarUrl;
  private Long categoryId;
  private Long productionId;
  private Long price;
  private Long quantity;
  private String status;
}
