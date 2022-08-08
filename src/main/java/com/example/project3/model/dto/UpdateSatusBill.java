package com.example.project3.model.dto;

import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSatusBill {

  private String status;
  private Long billId;
  private String reason;
}
