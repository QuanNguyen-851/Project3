package com.example.project3.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TurnoverEntity {

  private int billCount;
  private Long turnover;
  private Long importPrice;
  private Long interestRate;
}
