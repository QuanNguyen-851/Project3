package com.example.project3.controller;

import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product_information")
public class ProductInformationController {

  @Autowired
  private ProductInformationService service;

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseWrapper> deleteInfor(@RequestParam Long inforId) {
    var response =service.deleteInfor(inforId);
    if(response!=null){
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS,response), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.BAD_REQUEST);
  }
}
