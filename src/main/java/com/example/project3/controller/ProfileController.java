package com.example.project3.controller;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.ChangePassRequest;
import com.example.project3.model.dto.LoginDto;
import com.example.project3.model.dto.LoginResponse;
import com.example.project3.model.dto.ResetPassRequest;
import com.example.project3.model.entity.NewProfileResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProfileService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
  private Token tokenMapper;
  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ProfileService service;

  @PostMapping("/create")
  private ResponseEntity<ResponseWrapper> createProfile(@RequestBody ProfileEntity profileEntity) {
    Boolean isModerator = tokenMapper.isAccess(
        request,
        new ArrayList<>(List.of(RoleEnum.ADMIN,RoleEnum.EMPLOYEE,RoleEnum.SUPERADMIN))
    );
    if (profileEntity != null) {
      if(!isModerator){
        profileEntity.setRole(RoleEnum.USER.name());
      }
      return new ResponseEntity<>(service.createProfile(profileEntity), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @PostMapping("user/register")
  private ResponseEntity<ResponseWrapper> userCreateProfile(@RequestBody ProfileEntity profileEntity) {
    return new ResponseEntity<>(service.userCreateProfile(profileEntity), HttpStatus.OK);
  }
  @GetMapping("/getall")
  private ResponseEntity<List<ProfileEntity>> getAll(
      @RequestParam(value = "roles", required = false) List<RoleEnum> roles
      ) {
    return new ResponseEntity<>(service.getAll(roles), HttpStatus.OK);
  }

  @GetMapping("/getById")
  private ResponseEntity<ProfileEntity> getById(@RequestParam Long id) {
    if (id != null && service.getById(id) != null) {
      return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping("/signin")
  private ResponseEntity<ResponseWrapper> authenticateUser(@RequestBody LoginDto loginDto) {
    LoginResponse res = service.findByPhoneAndPassword(loginDto.getPhone(), loginDto.getPassword());
    if (res != null) {
      if (res.getToken().equals("err")) {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL, res, "tài khoản đã bị khóa không thể đăng nhập!"), HttpStatus.BAD_REQUEST);

      }
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res, "SUCCESS"), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, res, "Sai tên đăng nhâp hoặc mật khẩu"), HttpStatus.NOT_FOUND);
  }

  @PutMapping("/resetPass")
  private ResponseEntity<ResponseWrapper> resetPassword(
      @RequestBody ResetPassRequest resetPassRequest
  ) {
    var res = service.resetPassword(resetPassRequest.getMyRole(), resetPassRequest.getProfileId());
    if (res != null) {
      var succ = EnumResponse.SUCCESS;
      succ.setResponseMessage("Password mặc định là 123123! ");
      return new ResponseEntity<>(new ResponseWrapper(succ, res), HttpStatus.OK);
    }
    var err = EnumResponse.FAIL;
    err.setResponseMessage("Bạn không có quền hoặc không tìm thấy tài khoản này!");
    return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.BAD_REQUEST);
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

  @GetMapping("/blockProfile")
  private ResponseEntity<ResponseWrapper> blockProfile(
      @RequestParam Long id
  ) {
    var res = service.blockUser(id);
    if (res != null) {
      if (res.getRole() != null && res.getRole().equals(RoleEnum.SUPERADMIN.name())) {
        var err = EnumResponse.FAIL;
        err.setResponseMessage("Không thể thực hiện hành động với quản lý!");
        return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);
  }

  @PutMapping("/changePass")
  private ResponseEntity<ResponseWrapper> updatePass(
      @RequestBody ChangePassRequest changePassRequest
  ) {
    var res = service.changeMyPassword(changePassRequest.getOldPass(), changePassRequest.getNewPass(), changePassRequest.getMyId());
    if (res != null) {
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
    }
    var err = EnumResponse.FAIL;
    err.setResponseMessage("Không thể đổi mật khẩu !");
    return new ResponseEntity<>(new ResponseWrapper(err, res), HttpStatus.NOT_FOUND);

  }

  @PutMapping("/updateRole")
  private ResponseEntity<ResponseWrapper> updateRole(
      @RequestBody ProfileEntity profileEntity
  ) {
    Boolean isModerator = tokenMapper.isAccess(request,
        new ArrayList<>(List.of(RoleEnum.SUPERADMIN))
    );
    if(!isModerator){
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.ACCESSDENIED, null), HttpStatus.UNAUTHORIZED);
    }
    if (profileEntity.getRole() == null || profileEntity.getId() == null) {
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL, null), HttpStatus.BAD_REQUEST);
    }
    var res = service.updateUserRole(profileEntity);
    if (res != null) {
      if (res.getId().equals(0L)) {
        return new ResponseEntity<>(new ResponseWrapper(EnumResponse.FAIL, "không thể update SUPERADMIN role"), HttpStatus.OK);
      }
      return new ResponseEntity<>(new ResponseWrapper(EnumResponse.SUCCESS, res), HttpStatus.OK);
    }

    return new ResponseEntity<>(new ResponseWrapper(EnumResponse.NOT_FOUND, null), HttpStatus.NOT_FOUND);

  }
  @GetMapping("/countNewProfile")
  private ResponseEntity<NewProfileResponse> countNewProfile(
      @RequestParam(value = "role", required = false) String role,
      @RequestParam(value = "limit", required = false) Long limit
  ){
    return new ResponseEntity<>(service.countNewProfile(role,limit),HttpStatus.OK);
  }


}
