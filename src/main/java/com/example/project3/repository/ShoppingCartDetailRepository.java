package com.example.project3.repository;

import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.ShoppingCartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartDetailRepository extends JpaRepository<ShoppingCartDetailEntity, Long> {

  ShoppingCartDetailEntity findFirstByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);

}
