package com.example.project3.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassRequest {

  private String myRole;
  private Long profileId;
}
