package com.example.project3.service;

import com.example.project3.model.dto.LoginResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ProfileService {

  List<ProfileEntity> getAll();

  ProfileEntity getById(Long id);

  ResponseWrapper createProfile(ProfileEntity profileEntity);

  LoginResponse findByPhoneAndPassword(String phone, String pass);

  ProfileEntity resetPassword(String myRole, Long profileId);

  ProfileEntity updateMyProfile(ProfileEntity myProfile);

  ProfileEntity blockUser(Long id);

  ProfileEntity changeMyPassword(String oldPass, String newPass, Long ProfileId);

  ProfileEntity updateUserRole(ProfileEntity profileEntity);
}
