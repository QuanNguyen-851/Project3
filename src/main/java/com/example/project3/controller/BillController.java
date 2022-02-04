package com.example.project3.controller;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillService;
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
@RequestMapping("/bill")
public class BillController {

  @Autowired
  private BillService service;

  @GetMapping("/getall")
  private ResponseEntity<List<BillDTO>> getAll(
      @RequestParam(value = "profileId", required = false) Long profileId,
      @RequestParam(value = "phone", required = false) String phone,
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "type", required = false) String type
  ) {
    return new ResponseEntity<>(service.getAll(profileId, phone, status, type), HttpStatus.OK);
  }

  @GetMapping("/getByProfileId")
  private ResponseEntity<BillDTO> getByProfileId(
      @RequestParam Long profileId
  ) {
    return new ResponseEntity<>(service.getByProfileId(profileId),HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(
      @RequestBody BillDTO billdto
  ){
    return new ResponseEntity<>(service.create(billdto), HttpStatus.OK);
  }
}
