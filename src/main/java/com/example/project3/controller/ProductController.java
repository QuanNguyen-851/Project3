package com.example.project3.controller;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.NewProdResponse;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.UpdateQuantityRequest;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;

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
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/getall")
  private ResponseEntity<Iterable<ProductResponse>> getAll(
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "code", required = false) String code,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "idCate", required = false) Long idCate,
      @RequestParam(value = "idProduction", required = false) Long idProduction,
      @RequestParam(value = "getall", required = false ) Boolean getall
  ) {
    return new ResponseEntity<>(productService.getAll(
        status,
        code,
        name,
        idCate,
        idProduction,
        getall
    ), HttpStatus.OK);
  }

  @GetMapping("/getdetail")
  private ResponseEntity<ProductResponse> getDetail(@RequestParam Long id) {
    return new ResponseEntity<>(productService.getDetail(id), HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody ProductDTO request) {
    return new ResponseEntity<>(productService.create(request), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> update(@RequestBody ProductDTO request) {
    return new ResponseEntity<>(productService.update(request), HttpStatus.OK);
  }

  @PutMapping("/updateQuantity")
  private ResponseEntity<ResponseWrapper> updateQuantity(
      @RequestBody UpdateQuantityRequest updateQuantityRequest
  ) {
    if(updateQuantityRequest.getProductId()==null || updateQuantityRequest.getAction()==null){
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL, null), HttpStatus.BAD_REQUEST);
    }
    if (updateQuantityRequest.getNumber() != null && updateQuantityRequest.getNumber() <= 0) {
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL, updateQuantityRequest.getNumber()), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(productService.updateQuantity(updateQuantityRequest.getProductId(), updateQuantityRequest.getNumber(), updateQuantityRequest.getAction()), HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> deleteById(@RequestParam Long id) {
    return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
  }

  @PutMapping("/updateStatus")
  private ResponseEntity<ResponseWrapper> updateStatus(
      @RequestBody ProductResponse productResponse
  ){
    var res = productService.updateStatus(productResponse);
    if(res.getResponseData()==null){
      return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    if(res.getResponseData().equals("not found")){
      return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/CountNewProd")
  private ResponseEntity<NewProdResponse> countNewProd(
      @RequestParam(value = "limit", required = false) Long limit
  ){
   return new ResponseEntity<>(productService.countNewProd( limit), HttpStatus.OK);
  }
}
