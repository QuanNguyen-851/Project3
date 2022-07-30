package com.example.project3.Common;

import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.service.ProfileService;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class Token {

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private HttpServletRequest request;

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

  public String sub(String key){
    String token = decodeBase64(request.getHeader("Accept-Token"));
    if (StringUtils.isEmpty(token)) {
      return null;
    }
    List<String> properties = List.of(token.split("&"));
    HashMap<String, String> params = new HashMap<>();
    params.put("email", properties.get(0));
    params.put("phone", properties.get(1));
    params.put("id", properties.get(2));
    return  params.get(key);
  }
  public Boolean isAnonymous(){
    if(StringUtils.isEmpty(decodeBase64(request.getHeader("Accept-Token")))){
      return true;
    }
    return false;
  }
}
