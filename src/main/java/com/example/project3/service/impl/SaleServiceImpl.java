package com.example.project3.service.impl;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.SaleResponse;
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
  public SaleEntity createSale(SaleDTO saleDTO) {
    var exist = repository.findFirstByProductId(saleDTO.getProductId());
    SaleEntity saleEntity = new SaleEntity();
    if(exist!=null){
      //có rồi thì update lại
      saleEntity.setProductId(exist.getProductId());
      saleEntity.setSale(saleDTO.getSale());
      saleEntity.setStartDate(saleDTO.getStartDate().atStartOfDay());
      saleEntity.setEndDate(saleDTO.getEndDate().atTime(23,59,59));
      saleEntity.setCreatedBy(saleDTO.getCreatedBy());
      saleEntity.setModifiedDate(LocalDateTime.now());
      saleEntity.setCreatedDate(exist.getCreatedDate());
    }else {
      saleEntity.setProductId(saleDTO.getProductId());
      saleEntity.setSale(saleDTO.getSale());
      saleEntity.setStartDate(saleDTO.getStartDate().atStartOfDay());
      saleEntity.setEndDate(saleDTO.getEndDate().atTime(23, 59, 59));
      saleEntity.setCreatedBy(saleDTO.getCreatedBy());
      saleEntity.setModifiedDate(LocalDateTime.now());
      saleEntity.setCreatedDate(LocalDateTime.now());
    }
    return repository.save(saleEntity);
  }

  @Override
  public List<SaleResponse> getAll(Float sale, Boolean inUse) {
    return repository.getAllProdSale(sale, inUse);
  }

  @Override
  public SaleResponse getDetail(Long id, Long productId) {
    return repository.getDetail(id, productId);
  }
}
