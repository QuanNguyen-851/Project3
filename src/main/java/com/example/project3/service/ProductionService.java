package com.example.project3.service;

import com.example.project3.model.enumpk.DisableStatus;
import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ProductionService {
List<ProductionEntity>getAll();
ResponseWrapper create(ProductionEntity productionEntity);

ResponseWrapper update(ProductionEntity productionEntity);

ProductionEntity getById(Long id);

ProductionEntity deleteById(Long id);

ProductionEntity updateStatus( Long id , DisableStatus status);
}
