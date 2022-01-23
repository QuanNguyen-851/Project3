package com.example.project3.repository.custom;

import com.example.project3.model.entity.SaleEntity;
import java.util.List;

public interface SaleRepositoryCustom {
  List<SaleEntity> getall(String name, String key, Boolean isPercent);

}
