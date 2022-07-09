package com.example.project3.service;

import com.example.project3.response.ResponseWrapper;

public interface ShoppingCartService {

  ResponseWrapper createOrUpdate(Long productId, Long quantity);
}
