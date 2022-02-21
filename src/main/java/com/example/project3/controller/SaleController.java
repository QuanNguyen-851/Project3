package com.example.project3.controller;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.dto.VoucherDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.SaleResponse;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.SaleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {

  @Autowired
  private SaleService service;

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody SaleDTO saleDTO) {
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, service.createSale(saleDTO)), HttpStatus.OK);
  }

  @GetMapping("/getAll")
  private ResponseEntity<List<SaleResponse>> getAll(
      @RequestParam(value = "sale", required = false) Float sale,
      @RequestParam(value = "inUse", required = false) Boolean inUse
  ){
    return new ResponseEntity<>(service.getAll(sale, inUse),HttpStatus.OK);
  }
  @GetMapping("/getDetail")
  private ResponseEntity<ResponseWrapper> getDetail(
      @RequestParam(value = "saleId", required = false) Long saleId,
      @RequestParam(value = "productId", required = false) Long productId
  ){
    if(saleId ==null && productId == null){
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL,"=(((", "phải có một trong hai"),HttpStatus.BAD_REQUEST);

    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS,service.getDetail(saleId, productId)),HttpStatus.OK);
  }
}
