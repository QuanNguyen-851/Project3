package com.example.project3.repository;

import com.example.project3.model.entity.ProductSoldEntity;
import com.example.project3.repository.custom.ProductSoldRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSoldRepository extends JpaRepository<ProductSoldEntity, Long>, ProductSoldRepositoryCustom {

  ProductSoldEntity findFirstByProductId(Long prodId);
}
