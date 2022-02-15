package com.example.project3.controller;

import com.example.project3.model.entity.ProductSoldResponse;
import com.example.project3.service.ProdSoldService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ProductSold")
public class ProductSoldController {

  @Autowired
  private ProdSoldService service;
  @GetMapping("/getAll")
  private ResponseEntity<List<ProductSoldResponse>> getAll(
      @RequestParam(value = "productId", required = false) Long productId,
      @RequestParam(value = "month", required = false) String month,
      @RequestParam(value = "limit", required = false) Long limit
      ){
    var res = service.listProductSold(productId, month, limit);
    if(res!=null){
      return new ResponseEntity<>(res, HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
}
