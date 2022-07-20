package com.example.project3.repository.custom;

import com.example.project3.model.dto.NotificationResponse;
import com.example.project3.model.entity.NotificationEntity;
import java.util.List;

public interface NotificationRepositoryCustom {

  List<NotificationEntity> getNotification(Boolean isRead, Long profileId);
}
