package com.example.project3.repository.custom;

import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.ImportProductResponse;
import java.util.List;

public interface ImportProductRepositoryCustom {
List<ImportProductResponse> getByProductIdAndDate(Long prodId, String date, Long limit);
}
