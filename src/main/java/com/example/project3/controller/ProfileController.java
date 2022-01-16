package com.example.project3.controller;

import com.example.project3.model.dto.LoginDto;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProfileService;
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
@RequestMapping("/profile")
public class ProfileController {

  @Autowired
  private ProfileService service;

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> createProfile(@RequestBody ProfileEntity profileEntity) {
    if (profileEntity != null) {
      return new ResponseEntity<>(service.createProfile(profileEntity), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/getById")
  private ResponseEntity<ProfileEntity> getById(@RequestParam Long id) {
    if (id != null && service.getById(id) != null) {
      return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @PostMapping("/signin")
  public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
    return new ResponseEntity<>(service.findByPhoneAndPassword(loginDto.getPhone(), loginDto.getPassword()), HttpStatus.OK);
  }
}
