package com.example.project3.controller;

import com.example.project3.model.entity.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
  @GetMapping("/turnoverReport")
  private ResponseEntity<Iterable<ProductResponse>> getAll(
      @RequestParam(value = "month", required = false) Long month,
      @RequestParam(value = "year", required = false) Long year
  ) {
    return null;
  }
}
