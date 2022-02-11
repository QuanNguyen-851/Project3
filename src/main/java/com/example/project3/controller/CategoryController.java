package com.example.project3.controller;

import com.example.project3.model.enumpk.DisableStatus;
import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;


  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> createCategory(@RequestBody @Validated CategoryEntity categoryEntity) {
    var res = categoryService.createCategory(categoryEntity);
    if (res == null) {
      return new ResponseEntity<>(new ResponseWrapper(
          EnumResponse.EXIST,
          res,
          "Đã tồn tại sort name!"
      ), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new ResponseWrapper(
        EnumResponse.SUCCESS,
        res
    ), HttpStatus.OK);
  }

  @GetMapping("/getall")
  private ResponseEntity<List<CategoryEntity>> getAll(
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "name", required = false) String name
  ) {
    return new ResponseEntity<>(
        categoryService.getAllCategory(status, name), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> updateCategory(@RequestBody @Validated CategoryEntity categoryEntity) {
    return new ResponseEntity<>(categoryService.updateCategory(categoryEntity), HttpStatus.OK);
  }

  @GetMapping("/getdetail")
  private ResponseEntity<CategoryEntity> getById(@RequestParam Long id) {
    if (categoryService.getById(id) != null) {
      return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/delete")
  private ResponseEntity<ResponseWrapper> delete(@RequestParam Long id) {
    if (id != null) {
      var res = categoryService.deleteById(id);
      if (res != null) {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);
  }

  @PutMapping("/updateStatus")
  private ResponseEntity<ResponseWrapper> updateStatus(@RequestParam Long id, @RequestParam DisableStatus status) {
    if (status != null && id != null) {
      var re = categoryService.updateStatus(id, status);
      if (re != null) {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, re), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);

  }
}
