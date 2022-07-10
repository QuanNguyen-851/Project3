package com.example.project3.service;

import com.example.project3.model.dto.ShoppingCartRequest;
import com.example.project3.model.dto.ShoppingCartResponsePage;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ShoppingCartService {

  ResponseWrapper createOrUpdate(Long productId, Long quantity);

  ShoppingCartResponsePage getDetailShoppingCart();

  ResponseWrapper removeProduct(Long productId);

  ResponseWrapper updateProduct(ShoppingCartRequest request);
}
