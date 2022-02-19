package com.example.project3.repository.custom;

import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface ProductRepositoryCustom {
  ProductResponse getProductById(Long id);

  List<ProductResponse>getAllProduct(
      String status,
      String code,
      String name,
      Long idCate,
      Long idProduction,
      Boolean getAll
  );

  Long countNewProd(String moth);
  List<ProductResponse> getNewProd(String moth,  Long limit);

  Long countByStatus(String status);


}
