package com.example.project3.controller;

import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.service.ImportProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportPoductController {

  @Autowired
  private ImportProductService importProductService;

  @GetMapping("/getAll")
  private ResponseEntity<List<ImportProductResponse>> getAll(
      @RequestParam(value = "productId", required = false) Long productId
      , @RequestParam(value = "limit", required = false) Long limit,
      @RequestParam(value = "month", required = false) String month
      ) {
    return new ResponseEntity<>(importProductService.getAll(productId,month , limit), HttpStatus.OK);
  }
}
