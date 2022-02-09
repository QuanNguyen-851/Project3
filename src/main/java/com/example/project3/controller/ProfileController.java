package com.example.project3.controller;

import com.example.project3.model.dto.LoginDto;
import com.example.project3.model.dto.LoginResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProfileService;
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
@RequestMapping("/profile")
public class ProfileController {

  @Autowired
  private ProfileService service;

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> createProfile(@RequestBody ProfileEntity profileEntity) {
    if (profileEntity != null) {
      return new ResponseEntity<>(service.createProfile(profileEntity), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GetMapping("/getall")
  private ResponseEntity<List<ProfileEntity>> getAll() {
    return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }

  @GetMapping("/getById")
  private ResponseEntity<ProfileEntity> getById(@RequestParam Long id) {
    if (id != null && service.getById(id) != null) {
      return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @PostMapping("/signin")
  private ResponseEntity<ResponseWrapper> authenticateUser(@RequestBody LoginDto loginDto) {
    LoginResponse res = service.findByPhoneAndPassword(loginDto.getPhone(), loginDto.getPassword());
    if (res != null) {

      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, res), HttpStatus.NOT_FOUND);
  }

  @PostMapping("/resetPass")
  private ResponseEntity<ResponseWrapper> resetPassword(
      @RequestParam String myRole,
      @RequestParam Long profileId
  ) {
    var res = service.resetPassword(myRole, profileId);
    if (res != null) {
      var succ = EnumResponse.SUCCESS;
      succ.setResponseMessage("Password mặc định là 123123! ");
      return new ResponseEntity<>(new ResponseWrapper(succ, res), HttpStatus.OK);
    }
    var err = EnumResponse.FAIL;
    err.setResponseMessage("Bạn không có quền hoặc không tìm thấy tài khoản này!");
    return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.OK);
  }

  @PutMapping("/updateMyProfile")
  private ResponseEntity<ResponseWrapper> updateMyProfile(
      @RequestBody ProfileEntity myProfile
  ) {
    var res = service.updateMyProfile(myProfile);
    if (res != null) {
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);
  }

  @PostMapping("/blockProfile")
  private ResponseEntity<ResponseWrapper> blockProfile(
      @RequestParam Long id
  ) {
    var res = service.blockUser(id);
    if (res != null) {
      if (res.getRole() != null && res.getRole().equals(RoleEnum.SUPERADMIN.name())) {
        var err = EnumResponse.FAIL;
        err.setResponseMessage("Không thể thực hiện hành động với quản lý!");
        return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);
  }

  @PostMapping("/changePass")
  private ResponseEntity<ResponseWrapper> updatePass(
      @RequestParam String oldPass,
      @RequestParam String newPass,
      @RequestParam Long myId
  ) {
    var res = service.changeMyPassword(oldPass,newPass,myId);
    if(res!=null){
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
    }
    var err = EnumResponse.FAIL;
    err.setResponseMessage("Không không thể đổi mật khẩu !");
    return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.OK);

  }
}
