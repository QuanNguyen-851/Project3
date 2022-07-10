package com.example.project3.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShoppingCartResponsePage {
  private Long total;
  private List<ShoppingCartResponse> products;
}
