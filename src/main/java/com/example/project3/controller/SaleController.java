package com.example.project3.controller;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.service.SaleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {

  @Autowired
  private SaleService service;
  @GetMapping("/getall")
  private ResponseEntity<List<SaleEntity>> getAll() {
  return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }
  @PostMapping("/create")
  private ResponseEntity<SaleEntity> create(@RequestBody SaleDTO saleDTO){
    return new ResponseEntity<>(service.create(saleDTO), HttpStatus.OK);
  }
}
