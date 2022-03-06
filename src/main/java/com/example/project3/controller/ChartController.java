package com.example.project3.controller;

import com.example.project3.model.dto.BillDayOfMonth;
import com.example.project3.model.dto.ChartResponse;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.service.ChartService;
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
@RequestMapping("/chart")
public class ChartController {
  @Autowired
  private ChartService service;

  @GetMapping("/line")
  private ResponseEntity<List<ChartResponse>> getAll(
  ) {
    return new ResponseEntity<>(service.linechart(), HttpStatus.OK);
  }

  @GetMapping("/dayOfMonth")
  private ResponseEntity<List<BillDayOfMonth>> getBillOfMonth(){
    return new ResponseEntity<>(service.daychart(), HttpStatus.OK);
  }
}
