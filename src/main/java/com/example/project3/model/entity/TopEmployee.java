package com.example.project3.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "b_bill")
public class TopEmployee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long profileId;
  private Long billCount;
  @Transient
  private ProfileEntity profileEntity;
}
