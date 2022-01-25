package com.example.project3.service.impl;

import com.example.project3.model.enumpk.DisableStatus;
import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.repository.ProductionRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductionService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionServiceImpl implements ProductionService {

  @Autowired
  private ProductionRepository productionRepository;

  @Override
  public List<ProductionEntity> getAll(
      String status,
      String name
  ) {
    return productionRepository.getAll(status, name);
  }

  @Override
  public ResponseWrapper create(ProductionEntity productionEntity) {
    productionEntity.setCreatedDate(LocalDateTime.now());
    productionEntity.setModifiedDate(LocalDateTime.now());
    productionEntity.setStatus(DisableStatus.ENABLE.name());
     var re = productionRepository.save(productionEntity);
    return new ResponseWrapper(EnumResponse.SUCCESS, re );
  }

  @Override
  public ResponseWrapper update(ProductionEntity productionEntity) {
    var production = productionRepository.findFirstById(productionEntity.getId());
    if(production != null){
      productionEntity.setCreatedDate(production.getCreatedDate());
      productionEntity.setModifiedDate(LocalDateTime.now());
      return new ResponseWrapper(EnumResponse.SUCCESS, productionRepository.save(productionEntity));
    }else{
      var response = EnumResponse.FAIL;
      response.setResponseMessage("NOT FOUND");
      return new ResponseWrapper(response, null);
    }
  }

  @Override
  public ProductionEntity getById(Long id) {
    if(id!=null){
      return productionRepository.findFirstById(id);}
    return null;
  }

  @Override
  public ProductionEntity deleteById(Long id) {
    if(id!=null && productionRepository.findFirstById(id)!=null){
      var res = productionRepository.findFirstById(id);
      productionRepository.deleteById(id);
      return res;
    }
    return null;
  }

  @Override
  public ProductionEntity updateStatus(Long id, DisableStatus status) {
    ProductionEntity productionEntity = productionRepository.findFirstById(id);
    if(productionEntity!=null){
      productionEntity.setStatus(status.name());
      productionEntity.setModifiedDate(LocalDateTime.now());
      productionRepository.save(productionEntity);
      return productionEntity;
    }
    return null;
  }
}
