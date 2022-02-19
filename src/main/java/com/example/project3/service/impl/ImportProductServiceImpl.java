package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.repository.ImportProductRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.service.ImportProductService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportProductServiceImpl implements ImportProductService {

  @Autowired
  private ImportProductRepository repository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public ImportProductEntity save(ImportProductEntity importProductEntity) {
    importProductEntity.setCreatedDate(LocalDateTime.now());
    return repository.save(importProductEntity);
  }

  @Override
  public ImportProductEntity update(Long productId,
      Long importPrice,
      Long quantity,
      Long createBy) {
    String thisMonth = FormatDate.getThisMonth();
    var prod = productRepository.getProductById(productId);
    var number =  quantity - prod.getQuantity();
    if (number <= 0) {
      //do nothing
      return new ImportProductEntity();
    }
    ImportProductEntity importProductEntity = new ImportProductEntity();
    importProductEntity.setProductId(productId);
    importProductEntity.setImportQuantity(number);
    importProductEntity.setImportTotal(number * importPrice);
    importProductEntity.setCreatedDate(LocalDateTime.now());
    importProductEntity.setCreatedBy(createBy);
    return repository.save(importProductEntity);
  }

  @Override
  public List<ImportProductResponse> getAll(Long productId, Long limit) {
   var thismonth = FormatDate.getThisMonth();
    return repository.getByProductIdAndDate(productId,thismonth,limit);
  }


}
