package com.example.project3.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateQuantityRequest {
  Long productId;
  Long number;
  String action;
}
