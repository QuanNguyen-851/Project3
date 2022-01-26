package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.LoginResponse;
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
      profileEntity.setRole(RoleEnum.ADMIN.name());
      var sa = repository.save(profileEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, profileEntity);

    }
    return new ResponseWrapper(EnumResponse.FAIL, profileEntity);
  }

  @Override
  public LoginResponse findByPhoneAndPassword(String phone, String pass) {
    var res = repository.findFirstByPhoneAndPassWord(phone,pass);
    String token ;
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
}
