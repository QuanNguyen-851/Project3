package com.example.project3.repository.custom;

import com.example.project3.model.entity.SaleResponse;
import com.example.project3.repository.SaleRepository;
import java.util.List;

public interface SaleRepositoryCustom {
List<SaleResponse> getAllProdSale(Float sale, Boolean inUse);
  SaleResponse getDetail(Long saleId, Long productId);
}
