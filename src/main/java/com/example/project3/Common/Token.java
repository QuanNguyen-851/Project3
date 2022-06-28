package com.example.project3.Common;

import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.service.ProfileService;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class Token {

  @Autowired
  private ProfileRepository profileRepository;

  public String convertHashToString(String text) {
    String token = Base64.getEncoder().encodeToString(text.getBytes());
    return token;
  }

  public String decodeBase64(String token) {
    if(StringUtils.isEmpty(token)){
      return null;
    }
    return new String(Base64.getDecoder().decode(token));
  }

  public Boolean isAccess(HttpServletRequest request, List<RoleEnum> roles) {
    String token = decodeBase64(request.getHeader("Accept-Token"));
    if (StringUtils.isEmpty(token)) {
      return false;
    }
    List<String> properties = List.of(token.split("&"));
    String phone = properties.get(1);
    ProfileEntity entity= profileRepository.findFirstByPhone(phone);
    if(entity.getId()==null){
      return false;
    }
    for (RoleEnum role: roles) {
      if(role.equals(RoleEnum.valueOf(entity.getRole()))){
        return true;
      }
    };
    return false;
  }
}
