package com.example.project3.repository;

import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.repository.custom.WarrantyHistoryRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WarrantyHistoryRepository extends JpaRepository<WarrantyHistoryEntity, Long>, WarrantyHistoryRepositoryCustom {

  WarrantyHistoryEntity findFirstById(Long id);

  @Modifying
  @Query(value = "update p_warranty_history set product_condition = :condition where id = :id", nativeQuery = true)
  void updateCondition(String condition, Long id);
}
