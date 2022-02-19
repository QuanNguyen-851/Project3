package com.example.project3.service;

import com.example.project3.model.entity.ProductSoldResponse;
import java.util.List;

public interface ProdSoldService {
Void saveProdSold ( Long prodId, Long sold);

List<ProductSoldResponse>listProductSold(Long productId, String month, Long limit);

Long countProdSold(String month);
}
