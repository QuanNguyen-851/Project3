package com.example.project3.service;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import java.util.List;

public interface SaleService {

  List<SaleEntity> getAll();
  SaleEntity create(SaleDTO saleEntity);

}
