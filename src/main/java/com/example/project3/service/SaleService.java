package com.example.project3.service;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface SaleService {

  List<SaleEntity> getAll();

  ResponseWrapper create(SaleDTO saleEntity);

  ResponseWrapper update(SaleDTO saleEntity);

  ResponseWrapper delete(Long id);

}
