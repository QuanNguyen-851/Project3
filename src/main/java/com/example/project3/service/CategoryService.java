package com.example.project3.service;

import com.example.project3.model.enumpk.DisableStatus;
import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface CategoryService {

  List<CategoryEntity> getAllCategory(String status, String name);

  CategoryEntity createCategory(CategoryEntity categoryEntity);

  ResponseWrapper updateCategory(CategoryEntity categoryEntity);

  CategoryEntity getById(Long id);

  CategoryEntity deleteById(Long id);

  CategoryEntity updateStatus(Long id , DisableStatus disableStatus);
}
