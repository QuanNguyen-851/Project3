package com.example.project3.repository;

import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.ShoppingCartEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

  ShoppingCartEntity findFirstByProfileIdAndProductId(Long profileId, Long productId);

  List<ShoppingCartEntity>findAllByProfileIdOrderByIdDesc(Long profileId);

}
