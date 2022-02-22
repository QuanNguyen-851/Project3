package com.example.project3.service;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.SaleResponse;
import com.example.project3.repository.SaleRepository;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface SaleService {
SaleEntity createSale(SaleDTO saleDTO);
List<SaleResponse> getAll(Float sale, Boolean inUse);

SaleResponse getDetail(Long id, Long productId);
ResponseWrapper delete(Long id);
}
