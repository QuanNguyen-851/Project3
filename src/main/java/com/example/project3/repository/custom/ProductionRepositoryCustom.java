package com.example.project3.repository.custom;

import com.example.project3.model.dto.CategoryProductResponse;
import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.model.enumpk.SortByEnum;
import java.util.List;

public interface ProductionRepositoryCustom {
List<ProductionEntity> getAll(String status, String name, SortByEnum sortByEnum);
}
