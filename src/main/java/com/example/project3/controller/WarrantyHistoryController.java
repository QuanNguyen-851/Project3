package com.example.project3.controller;

import com.example.project3.model.dto.VoucherDTO;
import com.example.project3.model.dto.WarrantyHistoryResponse;
import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.WarrantyHistoryService;
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
@RequestMapping("/warrantyHistory")
public class WarrantyHistoryController {

  @Autowired
  private WarrantyHistoryService warrantyHistoryService;


  @GetMapping("/getall")
  private ResponseEntity<List<WarrantyHistoryResponse>> getAll(

      @RequestParam(value = "searchKey", required = false) String searchKey,
      @RequestParam(value = "profileId", required = false) Long profileId,
      @RequestParam(value = "status", required = false) WarrantyHistoryStatus status
      ) {
    return new ResponseEntity<>(warrantyHistoryService.getWarrantyHistory(searchKey, profileId, status), HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody WarrantyHistoryEntity entity) {
    return new ResponseEntity<>(warrantyHistoryService.createWarrantyHistory(entity), HttpStatus.OK);
  }

}
