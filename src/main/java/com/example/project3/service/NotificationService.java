package com.example.project3.service;

import com.example.project3.model.dto.NotificationRequest;
import com.example.project3.model.entity.NotificationEntity;

public interface NotificationService {

  Long createNotification(NotificationRequest request);
}
