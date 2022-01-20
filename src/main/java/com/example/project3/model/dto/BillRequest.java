package com.example.project3.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillRequest {
  private Long profileId;
  private Long saleId;
  private String description;
  private String ownerName;
  private String phone;
  private String email;
  private String address;
//  private List< >
}
