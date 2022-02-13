package com.example.project3.service;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.NewProdResponse;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ProductService {

  List<ProductResponse> getAll(
      String status,
      String code,
      String name,
      Long idCate,
      Long idProduction,
      Boolean getAll);

  ProductResponse getDetail(Long id);

  ResponseWrapper create(ProductDTO productDTO);

  ResponseWrapper update(ProductDTO productDTO);

  ResponseWrapper deleteById(Long id);

  ResponseWrapper updateQuantity(Long productId, Long number, String action);

  ResponseWrapper updateStatus(ProductResponse productResponse);

  NewProdResponse countNewProd( Long limit);
}
