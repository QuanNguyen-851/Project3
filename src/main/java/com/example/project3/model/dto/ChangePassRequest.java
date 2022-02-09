package com.example.project3.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class ChangePassRequest {

  String oldPass;
  String newPass;
  Long myId;
}
