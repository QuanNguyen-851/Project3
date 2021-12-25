package com.example.project3.controller;

import com.example.project3.model.entity.testEntity;
import com.example.project3.service.testService;
import java.util.List;
import org.apache.catalina.filters.AddDefaultCharsetFilter.ResponseWrapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
@Autowired
private testService testService;
  @GetMapping("/test")
  public ResponseEntity<ResponseBody> test(){
    Long rs= testService.getbyid();
    System.out.println(rs+"oke");
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
