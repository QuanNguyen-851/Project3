package com.example.project3.repository;

import com.example.project3.model.entity.SaleEntity;
import com.example.project3.repository.custom.SaleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleEntity, Long>, SaleRepositoryCustom {

  SaleEntity findFirstByProductId(Long productId);

  SaleEntity findFirstById(Long id);

}
