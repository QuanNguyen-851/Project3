package com.example.project3.repository;

import com.example.project3.model.entity.ProductEntity;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {

//  @Query(value = "select pp.* from p_product pp " , nativeQuery = true)
//  Page<ProductEntity> getAllProduct(Pageable pageable);

  //  Page<ProductEntity> getAllBy(Pageable pageable);

//  @Query(value = "select p_product.*from p_product order by id DESC limit 1 ", nativeQuery = true)
//  ProductEntity getNewProduct();

  ProductEntity findFirstById(Long id);

  Long countAllByCategoryId(Long id);
  Long countAllByProductionId(Long id);
  List<ProductEntity> findAllByCategoryId(Long id);
  List<ProductEntity> findAllByProductionId(Long id);


}
