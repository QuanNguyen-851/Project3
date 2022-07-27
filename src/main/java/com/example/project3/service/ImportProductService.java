package com.example.project3.service;

import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.ImportProductResponse;
import java.util.List;

public interface ImportProductService {
  ImportProductEntity save(ImportProductEntity importProductEntity);

  ImportProductEntity update(
      Long productId,
      Long importPrice,
      Long quantity,
      Long createBy
  );
  List<ImportProductResponse> getAll(Long productId, String month ,Long limit);
}
