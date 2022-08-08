package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.NotificationRequest;
import com.example.project3.model.dto.NotificationResponse;
import com.example.project3.model.entity.NotificationEntity;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.repository.NotificationRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.service.NotificationService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class NotificationServiceImpl implements NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private Token token;

  @Autowired
  private ProfileRepository profileRepository;

  @Override
  public Long createNotification(NotificationRequest request) {
    String param = null;
    if (request.getParams() != null) {
      param = request.getParams().toString();
    }
    for (Long profileId : request.getProfileId()
    ) {
      notificationRepository.save(NotificationEntity.builder()
          .senderId(request.getSenderId())
          .profileId(profileId)
          .title(request.getTitle())
          .body(request.getBody())
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

  @Override
  public List<NotificationResponse> getAllNotification(Boolean isRead) {
    Long id = Long.parseLong(token.sub("id"));
    ProfileEntity profile = profileRepository.findFirstById(id );
    if(Objects.isNull(profile)){
      return new ArrayList<>();
    }
    return notificationRepository.getNotification(isRead, profile.getId())
        .stream().map(val -> NotificationResponse.builder()
            .id(val.getId())
            .body(val.getBody())
            .isRead(val.getIsRead())
            .createdBy(val.getCreatedBy())
            .createdDate(val.getCreatedDate())
            .modifiedBy(val.getModifiedBy())
            .modifiedDate(val.getModifiedDate())
            .params(new JsonObject().getAsJsonObject(val.getParams()))
            .title(val.getTitle())
            .profileId(val.getProfileId())
            .senderId(val.getSenderId())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public Long readNotification(Long id) {
    var entity = notificationRepository.findFirstById(id);
    if (entity.getId() == null) {
      return id;
    }
    entity.setIsRead(Boolean.TRUE);
    entity.setModifiedDate(LocalDateTime.now());
    var res = notificationRepository.save(entity);
    return res.getId();
  }

  @Override
  public Long countUnRead() {
    Long myId = Long.parseLong(token.sub("id"));
    return notificationRepository.countAllByProfileIdAndIsRead(myId, false);
  }
}
