package com.example.project3.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetailRequest {
private Long billId;
private Long productId;
private Long quantity;
private Long price;
}
