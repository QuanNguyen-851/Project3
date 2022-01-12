package com.example.project3.service;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.Producttest;
import com.example.project3.response.ResponseWrapper;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Page<ProductResponse> getAll(int page, int size);

  ProductResponse getDetail(Long id);

  ResponseWrapper create(ProductResponse productResponse);


}
