package com.example.project3.repository.custom;

import com.example.project3.model.entity.ImageEntity;
import java.util.List;

public interface ImageRepositoryCustom {

  List<ImageEntity> getProductImageByIdProduct(Long id);

}
