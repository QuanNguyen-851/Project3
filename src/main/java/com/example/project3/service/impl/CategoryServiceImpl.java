package com.example.project3.service.impl;

import com.example.project3.model.enumpk.DisableStatus;
import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.repository.CategoryRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.CategoryService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<CategoryEntity> getAllCategory(String status, String name)
  {

    var res = categoryRepository.getAll(status, name);
    for (CategoryEntity cate: res) {
     cate.setCountProd(productRepository.countAllByCategoryId(cate.getId()));
    }
    return res;
  }

  @Override
  public CategoryEntity createCategory(CategoryEntity categoryEntity) {
    categoryEntity.setSortName(categoryEntity.getSortName().toUpperCase(Locale.ROOT));
    var cate = categoryRepository.findFirstBySortName(categoryEntity.getSortName());
    if(cate!=null){
      return null;
    }
    categoryEntity.setCreatedDate(LocalDateTime.now());
    categoryEntity.setModifiedDate(LocalDateTime.now());
    categoryEntity.setStatus(DisableStatus.ENABLE.name());
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

  @Override
  public CategoryEntity getById(Long id) {
    if(id!=null){
    var res= categoryRepository.findFirstById(id);
    res.setCountProd(productRepository.countAllByCategoryId(id));
    return res;
    }
    return null;
  }

  @Override
  public CategoryEntity deleteById(Long id) {
    if(id!=null && categoryRepository.findFirstById(id)!=null){
      var res = categoryRepository.findFirstById(id);
      categoryRepository.deleteById(id);
      return res;
    }
    return null;
  }

  @Override
  public CategoryEntity updateStatus(Long id, DisableStatus disableStatus) {
    var re = categoryRepository.findFirstById( id);
    if(re!=null){
      re.setStatus(disableStatus.name());
      re.setModifiedDate(LocalDateTime.now());
      categoryRepository.save(re);
      return  re;
    }
    return null;
  }


}
