package com.example.project3.controller;

import com.example.project3.model.dto.BillDetailImeiRequest;
import com.example.project3.model.dto.BillDetailRequest;
import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.repository.BillDetailImeiRepository;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillDetailImeiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imei")
public class ImeiController {

  @Autowired
  private BillDetailImeiService billDetailImeiService;

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(
      @RequestBody @Validated BillDetailImeiRequest request
  ) {
    return new ResponseEntity<>(billDetailImeiService.create(request), HttpStatus.OK);
  }
}
