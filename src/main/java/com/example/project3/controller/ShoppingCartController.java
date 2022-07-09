package com.example.project3.controller;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @PostMapping("/add")
  private ResponseEntity<ResponseWrapper> createOrUpdate(
      @RequestParam(value = "productId", required = true) Long productId,
      @RequestParam(value = "quantity", required = true) Long quantity
      ) {
    return new ResponseEntity<>(shoppingCartService.createOrUpdate(productId, quantity), HttpStatus.OK);
  }

}
