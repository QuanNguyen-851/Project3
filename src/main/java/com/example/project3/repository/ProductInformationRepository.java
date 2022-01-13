package com.example.project3.repository;

import com.example.project3.model.entity.ProductInformationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInformationRepository extends JpaRepository<ProductInformationEntity, Long> {

  List<ProductInformationEntity> findAllByProductId(Long ProductId);

  ProductInformationEntity findFirstById(Long id);

  ProductInformationEntity findFirstByProductIdAndKey(Long productId, String key);
}