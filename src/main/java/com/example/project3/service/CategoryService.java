package com.example.project3.service;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface CategoryService {

  List<CategoryEntity> getAllCategory();

  CategoryEntity createCategory(CategoryEntity categoryEntity);

  ResponseWrapper updateCategory(CategoryEntity categoryEntity);

  CategoryEntity getById(Long id);


}
