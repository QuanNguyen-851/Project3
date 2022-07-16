package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.NotificationRequest;
import com.example.project3.model.entity.NotificationEntity;
import com.example.project3.repository.NotificationRepository;
import com.example.project3.service.NotificationService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private Token token;

  @Override
  public Long createNotification(NotificationRequest request) {
    String param = null;
    if(request.getParams()!=null){
       param = request.getParams().toString();
    }
    for (Long profileId : request.getProfileId()
    ) {
      var res = notificationRepository.save(NotificationEntity.builder()
          .senderId(request.getSenderId())
          .profileId(profileId)
          .title(request.getTitle())
          .body(request.getTitle())
          .isRead(Boolean.FALSE)
          .params(param)
          .createdBy(Long.parseLong(token.sub("id")))
          .createdDate(LocalDateTime.now())
          .modifiedBy(Long.parseLong(token.sub("id")))
          .modifiedDate(LocalDateTime.now())
          .build());
    }

    return 1L;
  }
}
