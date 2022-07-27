package com.example.project3.repository.custom;

import com.example.project3.model.dto.CategoryProductResponse;
import com.example.project3.model.entity.ProductionEntity;
import java.util.List;

public interface ProductionRepositoryCustom {
List<ProductionEntity> getAll(String status, String name);
}
