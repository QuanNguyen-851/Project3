package com.example.project3.repository.custom;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.enumpk.SortByEnum;
import java.util.List;

public interface CategoryRepositoryCustom {
  List<CategoryEntity>getAll(String status, String name, SortByEnum sortByEnum);

  List<CategoryEntity>getALlCateHasProd();
}
