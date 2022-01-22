package com.example.project3.controller;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/getall")
  private ResponseEntity<Iterable<ProductResponse>> getAll() {
    return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
  }
  @GetMapping("/getdetail")
  private ResponseEntity<ProductResponse> getDetail(@RequestParam Long id){
    return new ResponseEntity<>(productService.getDetail(id), HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody ProductResponse request){
    return new ResponseEntity<>(productService.create(request), HttpStatus.OK);
  }
  @PutMapping ("/update")
  private ResponseEntity<ResponseWrapper> update(@RequestBody ProductResponse request){
    return new ResponseEntity<>(productService.update(request), HttpStatus.OK);
  }
  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> deleteById(@RequestParam Long id){
    return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
  }

}
