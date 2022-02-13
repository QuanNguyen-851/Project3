package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.Common.Token;
import com.example.project3.model.dto.LoginResponse;
import com.example.project3.model.entity.NewProfileResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProfileService;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private ProfileRepository repository;

  @Override
  public List<ProfileEntity> getAll() {
    return repository.findAll();
  }

  @Override
  public ProfileEntity getById(Long id) {
    var re = repository.findFirstById(id);

    return repository.findFirstById(id);
  }

  @Override
  public ResponseWrapper createProfile(ProfileEntity profileEntity) {
    if (profileEntity.getPhone() != null && profileEntity.getEmail() != null) {
      String phone = profileEntity.getPhone();
      String email = profileEntity.getEmail();
      if (repository.findFirstByPhone(phone) != null) {
        return new ResponseWrapper(EnumResponse.PHONEXIST, profileEntity);
      } else if (repository.findFirstByEmail(email) != null) {
        return new ResponseWrapper(EnumResponse.EMAILEXIST, profileEntity);
      }
      profileEntity.setCreatedDate(LocalDateTime.now());
      profileEntity.setModifiedDate(LocalDateTime.now());
      if(profileEntity.getRole().equals(RoleEnum.SUPERADMIN.name())){
        return new ResponseWrapper(EnumResponse.FAIL, profileEntity, "không thể tạo tài khoản superadmin");
      }
      var sa = repository.save(profileEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, profileEntity);

    }
    return new ResponseWrapper(EnumResponse.FAIL, profileEntity);
  }

  @Override
  public LoginResponse findByPhoneAndPassword(String phone, String pass) {
    var res = repository.findFirstByPhoneAndPassWord(phone,pass);
    String token ;
    if(res.getBlock().equals(true)){
      return LoginResponse.builder()
          .token("err").build();
    }
    if(res!=null){
      var temp = res.getEmail() + res.getPhone()+ res.getPassWord();
      token = Token.convertHashToString(temp);

      return LoginResponse.builder()
          .token(token)
          .role(res.getRole())
          .profile(res)
          .build();
    }
    return null;
  }

  @Override
  public ProfileEntity resetPassword(String myRole, Long profileId) {
    var profile= repository.findFirstById(profileId);
    if(profile!=null && myRole.equals(RoleEnum.SUPERADMIN.name())){
      profile.setPassWord("123123");
      return repository.save(profile);
    }
    return null;
  }

  @Override
  public ProfileEntity updateMyProfile(ProfileEntity myProfile) {
    var profile = repository.findFirstById(myProfile.getId());
    if(profile!=null){
      String role = profile.getRole();
      myProfile.setCreatedDate(profile.getCreatedDate());
      myProfile.setModifiedDate(LocalDateTime.now());
      myProfile.setBlock(profile.getBlock());
      myProfile.setPassWord(profile.getPassWord());
      myProfile.setPhone(profile.getPhone());
      if(role!=null && role.equals(RoleEnum.SUPERADMIN.name())){
        myProfile.setRole(RoleEnum.SUPERADMIN.name());
      }
      return repository.save(myProfile);
    }
    return null;
  }

  @Override
  public ProfileEntity blockUser(Long id) {
    var profile = repository.findFirstById(id);
    if(profile!=null){
      String role = profile.getRole();
      if(role!=null && role.equals(RoleEnum.SUPERADMIN.name())){
        profile.setBlock(false);
      }else{
        if(profile.getBlock()!=null && profile.getBlock().equals(false)){
          profile.setBlock(true);
        }else{
        profile.setBlock(false);
        }
      }
      return repository.save(profile);
    }
    return null;
  }

  @Override
  public ProfileEntity changeMyPassword(String oldPass,String newPass, Long ProfileId) {
    var profile = repository.findFirstById(ProfileId);
    if(profile!=null &&newPass!=null && profile.getPassWord().equals(oldPass)){
      profile.setPassWord(newPass);
      return repository.save(profile);
    }
    return null;
  }

  @Override
  public ProfileEntity updateUserRole(ProfileEntity profileEntity) {
    if(profileEntity.getRole().toUpperCase(Locale.ROOT).equals(RoleEnum.SUPERADMIN)){
      profileEntity.setId(0L);
      return profileEntity;
    }
    var pro = repository.findFirstById(profileEntity.getId());
    if(pro!=null){
      if(pro.getRole().equals(RoleEnum.SUPERADMIN.name())){
        profileEntity.setId(0L);
        return profileEntity;
      }
      pro.setRole(profileEntity.getRole().toUpperCase(Locale.ROOT));
      return repository.save(pro);
    }
    return null;
  }

  @Override
  public NewProfileResponse countNewProfile(String role, Long limit) {
    var thismonth = FormatDate.getThisMonth();
    var count = repository.countNewProfile(role,thismonth);
    var prof = repository.getNewProfile(role, thismonth, limit);
    return NewProfileResponse.builder()
        .count(count)
        .data(prof)
        .build();
  }

}
