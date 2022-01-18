package com.example.project3.service;

import com.example.project3.model.dto.LoginResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.response.ResponseWrapper;

public interface ProfileService {

  ProfileEntity getById(Long id);

  ResponseWrapper createProfile(ProfileEntity profileEntity);

  LoginResponse findByPhoneAndPassword(String phone, String pass);

}
