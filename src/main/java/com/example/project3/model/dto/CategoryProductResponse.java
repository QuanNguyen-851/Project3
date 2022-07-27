package com.example.project3.model.dto;

import com.example.project3.model.entity.ProductResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryProductResponse {
  private Long categoryId;
  private String categoryName;
  private String title;
  private int total;
  private List<ProductResponse> products;
}
