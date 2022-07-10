package com.example.project3.model.dto;

import com.example.project3.model.entity.ProductResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoppingCartResponse {
  public Long quantity;
  public ProductResponse product;
  public LocalDateTime createDate;
}
