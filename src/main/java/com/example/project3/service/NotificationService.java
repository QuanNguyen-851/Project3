package com.example.project3.service;

import com.example.project3.model.dto.NotificationRequest;
import com.example.project3.model.dto.NotificationResponse;
import com.example.project3.model.entity.NotificationEntity;
import java.util.List;

public interface NotificationService {

  Long createNotification(NotificationRequest request);

  List<NotificationResponse> getAllNotification(Boolean isRead);

  Long readNotification(Long id);
  Long countUnRead();
}
