package com.example.project3.service.impl;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.repository.ImageRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository repository;

  @Override
  public List<ImageEntity> getImageByProductId(Long id) {
    return repository.getProductImageByIdProduct(id);
  }
}
