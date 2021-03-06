package com.example.project3.service.impl;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.repository.ImageRepository;
import com.example.project3.repository.ProductImageRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository repository;
  @Autowired
  private ProductImageRepository productImageRepository;

  @Override
  public List<ImageEntity> getImageByProductId(Long id) {
    return repository.getProductImageByIdProduct(id);
  }

  @Override
  public ImageEntity deleteImateById(Long id) {
    var image = repository.findFirstById(id);
    if(image!=null){
       repository.deleteById(id);
       productImageRepository.deleteAllByImageId(id);
       return image;
    }
    return null;
  }
}
