package com.example.project3.repository;

import com.example.project3.model.entity.ProductInformationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductInformationRepository extends JpaRepository<ProductInformationRepository, Long> {
@Query(value = "")
  List<ProductInformationEntity> findAllByProduct(String productCode, Long ProductId);
}