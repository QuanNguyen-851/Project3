package com.example.project3.model.dto;

import com.example.project3.model.entity.ProfileEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {
  String token;
  String role;
 ProfileEntity profile;

}
