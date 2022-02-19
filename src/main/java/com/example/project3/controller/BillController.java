package com.example.project3.controller;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.UpdateSatusBill;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.model.entity.TurnoverEntity;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "startDate", required = false) Date startDate,
      @RequestParam(value = "endDate", required = false) Date endDate
  ) {
    return new ResponseEntity<>(service.getAll(profileId, phone, status, type, startDate, endDate), HttpStatus.OK);
  }

  @GetMapping("/getById")
  private ResponseEntity<BillDTO> getById(
      @RequestParam Long billId
      ) {
    var res = service.getById(billId);
    if(res!=null){
      return new ResponseEntity<>(res,HttpStatus.OK);
    }
    return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);

  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(
      @RequestBody BillDTO billdto
  ){
    return new ResponseEntity<>(service.create(billdto), HttpStatus.OK);
  }

  @PutMapping("/updateStatus")
  private ResponseEntity<ResponseWrapper> updateStatus(
      @RequestBody UpdateSatusBill updateSatusBill
  ){
    return new ResponseEntity<>(service.updateStatus(updateSatusBill.getBillId(), updateSatusBill.getStatus()), HttpStatus.OK);
  }
  @GetMapping("/countNewBill")
  private ResponseEntity<NewBillResponse> countBill(
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "type", required = false) String type
  ){
    return new  ResponseEntity<>(service.countNewBill(status,type), HttpStatus.OK) ;
  }
  @GetMapping("/getTurnover")
  private ResponseEntity<TurnoverEntity> getTurnover(
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "type", required = false) String type
  ){
    return new ResponseEntity<>(service.getTurnover(status,type, null), HttpStatus.OK);
  }
  @GetMapping("/getTopEmployee")
  private ResponseEntity<List<TopEmployee>> getTopEmployee(
      @RequestParam(value = "limit", required = false) Long limit,
      @RequestParam(value = "profileRole", required = false) String profileRole
  ){
    return new ResponseEntity<>(service.getTopEmployee(limit,profileRole), HttpStatus.OK);
  }
}
