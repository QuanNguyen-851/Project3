package com.example.project3.repository;

import com.example.project3.model.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

  @Modifying
  @Query(value = "delete from pi_product_image where image_id = :id", nativeQuery = true)
  Integer deleteAllByImageId(Long id);
}
