package com.example.project3.service.impl;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.repository.CategoryRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.CategoryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<CategoryEntity> getAllCategory() {
    return categoryRepository.findAll();
  }

  @Override
  public CategoryEntity createCategory(CategoryEntity categoryEntity) {
    categoryEntity.setCreatedDate(LocalDateTime.now());
    return categoryRepository.save(categoryEntity);
  }

  @Override
  public ResponseWrapper updateCategory(CategoryEntity categoryEntity) {
    var category = categoryRepository.findFirstById(categoryEntity.getId());

    if (category != null) {
      categoryEntity.setModifiedDate(LocalDateTime.now());
      categoryEntity.setCreatedDate(category.getCreatedDate());
      return new ResponseWrapper(EnumResponse.SUCCESS,
          categoryRepository.save(categoryEntity));
    } else {
      var response = EnumResponse.FAIL;
      response.setResponseMessage("NOT FOUND");
      return new ResponseWrapper(response, null);
    }
  }


}
