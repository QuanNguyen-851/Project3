package com.example.project3.controller;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.dto.ShoppingCartRequest;
import com.example.project3.model.dto.ShoppingCartResponsePage;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ShoppingCartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/detail")
  private ResponseEntity<ShoppingCartResponsePage> getDetailShoppingCart() {
    return new ResponseEntity<>(shoppingCartService.getDetailShoppingCart(), HttpStatus.OK);
  }

  @DeleteMapping("/remove")
  private ResponseEntity<ResponseWrapper> removeProduct(
      @RequestParam(value = "productId", required = true) Long productId
  ) {
    return new ResponseEntity<>(shoppingCartService.removeProduct(productId), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> updateShoppingCart(
      @RequestBody ShoppingCartRequest request
  ) {
    return new ResponseEntity<>(shoppingCartService.updateProduct(request), HttpStatus.OK);
  }

}
