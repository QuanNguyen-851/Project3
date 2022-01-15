package com.example.project3.service;

import com.example.project3.model.entity.ProductResponse;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ProductService {

  List<ProductResponse> getAll();

  ProductResponse getDetail(Long id);

  ResponseWrapper create(ProductResponse productResponse);

  ResponseWrapper update(ProductResponse productResponse);
}
