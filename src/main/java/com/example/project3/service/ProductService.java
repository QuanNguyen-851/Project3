package com.example.project3.service;

import com.example.project3.model.dto.CategoryProductResponse;
import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.NewProdResponse;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.enumpk.OrderEnum;
import com.example.project3.model.enumpk.SortByEnum;
import com.example.project3.response.ResponseWrapper;
import java.util.Date;
import java.util.List;

public interface ProductService {

  List<ProductResponse> getAll(
      String status,
      String code,
      String name,
      Long idCate,
      Long idProduction,
      Boolean getAll,
      Long limit,
      SortByEnum sortByEnum,
      OrderEnum orderEnum,
      Long startDate,
      Long endDate
  );

  List<CategoryProductResponse> userGetAll();

  ProductResponse getDetail(Long id);

  ResponseWrapper create(ProductDTO productDTO);

  ResponseWrapper update(ProductDTO productDTO);

  ResponseWrapper deleteById(Long id);

  ResponseWrapper updateQuantity(Long productId, Long number, String action);

  ResponseWrapper updateStatus(Long id, String status);

  NewProdResponse countNewProd( Long limit);

  Long countByStatus(String status);
}
