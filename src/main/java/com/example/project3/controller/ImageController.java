package com.example.project3.controller;

import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService service;

  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> deleteById(@RequestParam Long id) {
    var image = service.deleteImateById(id);
    if (image != null) {
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, image), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);

  }
}
