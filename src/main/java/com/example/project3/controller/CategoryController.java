package com.example.project3.controller;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    return new ResponseEntity<>(new ResponseWrapper(
        EnumResponse.SUCCESS,
        categoryService.createCategory(categoryEntity)
    ), HttpStatus.OK);
  }

  @GetMapping("/getall")
  private ResponseEntity<List<CategoryEntity>> getAll() {
    return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK);
  }

  @PutMapping("/update")
  private ResponseEntity<ResponseWrapper> updateCategory(@RequestBody @Validated CategoryEntity categoryEntity){
    return new  ResponseEntity<>(categoryService.updateCategory(categoryEntity), HttpStatus.OK);
  }
  @GetMapping("/getdetail")
  private ResponseEntity<CategoryEntity> getById(@RequestParam Long id){
    if(categoryService.getById(id)!=null){
      return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }
}
