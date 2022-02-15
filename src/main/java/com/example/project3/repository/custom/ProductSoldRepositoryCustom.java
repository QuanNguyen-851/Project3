package com.example.project3.repository.custom;

import com.example.project3.model.entity.ProductSoldEntity;
import com.example.project3.model.entity.ProductSoldResponse;
import java.util.List;

public interface ProductSoldRepositoryCustom {
ProductSoldEntity getProdSold(Long prodId, String month);

List<ProductSoldResponse> listProductSold(Long prodId, String month, Long limit);
}
