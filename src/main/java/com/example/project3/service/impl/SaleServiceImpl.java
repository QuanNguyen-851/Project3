package com.example.project3.service.impl;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.repository.SaleRepository;
import com.example.project3.service.SaleService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
@Autowired
private SaleRepository repository;
  @Override
  public List<SaleEntity> getAll() {
    return repository.findAll();
  }

  @Override
  public SaleEntity create(SaleDTO saleDTO) {
    SaleEntity saleEntity = new SaleEntity();
    saleEntity.setCreatedDate(LocalDateTime.now());
    var start = saleDTO.getStartDate();
    var end = saleDTO.getEndDate();
    saleEntity.setStartDate( start.atStartOfDay());
    saleEntity.setEndDate(end.atTime(23, 59,59));
    repository.save(saleEntity);
    return saleEntity;
  }
}
