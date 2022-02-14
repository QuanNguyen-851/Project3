package com.example.project3.controller;

import com.example.project3.model.dto.VoucherDTO;
import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.VoucherService;
import java.util.List;
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
@RequestMapping("/voucher")
public class VoucherController {

  @Autowired
  private VoucherService service;

  @GetMapping("/getall")
  private ResponseEntity<List<VoucherEntity>> getAll(

      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "key", required = false) String key,
      @RequestParam(value = "isPercent", required = false) Boolean isPercent
  ) {
    return new ResponseEntity<>(service.getAll(name, key, isPercent), HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody VoucherDTO voucherDTO) {
    return new ResponseEntity<>(service.create(voucherDTO), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> update(@RequestBody VoucherDTO voucherDTO) {
    return new ResponseEntity<>(service.update(voucherDTO), HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> delete(@RequestParam Long id) {
    return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
  }

  @GetMapping("/getById")
  private ResponseEntity<VoucherEntity> getById(@RequestParam Long id) {
    var res = service.findById(id);
    if(res !=null){
      return new ResponseEntity<>(res, HttpStatus.OK);
    }
    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
  }
  @GetMapping("/getByKey")
  private ResponseEntity<VoucherEntity> getByKey(@RequestParam String key) {
    var res = service.findByKey(key);
    if(res !=null){
      return new ResponseEntity<>(res, HttpStatus.OK);
    }
    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
  }
}
