package com.example.project3.service.impl;

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
  public List<ProductionEntity> getAll() {
    return productionRepository.findAll();
  }

  @Override
  public ResponseWrapper create(ProductionEntity productionEntity) {
    productionEntity.setCreatedDate(LocalDateTime.now());
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
}
