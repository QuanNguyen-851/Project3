package com.example.project3.service;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;
import org.springframework.stereotype.Service;

public interface ImageService {
List<ImageEntity> getImageByProductId (Long id);

ImageEntity deleteImateById(Long id);
}
