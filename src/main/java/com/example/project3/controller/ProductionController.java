package com.example.project3.controller;

import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/production")
public class ProductionController {

  @Autowired
  private ProductionService productionService;

  @GetMapping("/getall")
  private ResponseEntity<List<ProductionEntity>> getAll() {
    return new ResponseEntity<>(productionService.getAll(), HttpStatus.OK);
  }
  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create( @RequestBody ProductionEntity productionEntity){
  return new ResponseEntity<>(productionService.create(productionEntity), HttpStatus.OK);
  }
  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> update( @RequestBody ProductionEntity productionEntity){
    return new ResponseEntity<>(productionService.update(productionEntity), HttpStatus.OK);

  }
}
