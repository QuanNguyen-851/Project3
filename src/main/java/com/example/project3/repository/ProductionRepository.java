package com.example.project3.repository;

import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.repository.custom.ProductionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<ProductionEntity, Long>, ProductionRepositoryCustom {
ProductionEntity findFirstById(Long id);
}
