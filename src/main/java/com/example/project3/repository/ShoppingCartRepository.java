package com.example.project3.repository;

import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

  ShoppingCartEntity findFirstByProfileId(Long profileId);
}
