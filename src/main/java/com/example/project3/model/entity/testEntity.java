package com.example.project3.model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="t_test")
public class testEntity {
  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
}
