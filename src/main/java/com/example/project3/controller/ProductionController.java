package com.example.project3.controller;

import com.example.project3.model.DisableStatus;
import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/production")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ProductionController {

  @Autowired
  private ProductionService productionService;

  @GetMapping("/getall")
  private ResponseEntity<List<ProductionEntity>> getAll() {
    return new ResponseEntity<>(productionService.getAll(), HttpStatus.OK);
  }

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> create(@RequestBody ProductionEntity productionEntity) {
    return new ResponseEntity<>(productionService.create(productionEntity), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> update(@RequestBody ProductionEntity productionEntity) {
    return new ResponseEntity<>(productionService.update(productionEntity), HttpStatus.OK);
  }

  @GetMapping("/getdetail")
  private ResponseEntity<ProductionEntity> getDetail(@RequestParam Long id) {
    if (productionService.getById(id) != null) {
      return new ResponseEntity<>(productionService.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }
  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> deleteById(@RequestParam Long id) {
    if(id!=null){
      var res = productionService.deleteById(id);
      if (res !=null){
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.BAD_REQUEST);
  }

  @PutMapping("/updateStatus")
  private ResponseEntity<ResponseWrapper> updateStatus(@RequestParam Long id, @RequestParam DisableStatus status) {
    if (status != null && id != null) {
      var re = productionService.updateStatus(id, status);
      if (re != null) {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, re), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.BAD_REQUEST);

  }
}
