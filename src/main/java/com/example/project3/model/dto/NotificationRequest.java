package com.example.project3.model.dto;

import com.google.gson.JsonObject;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationRequest {
  private Long senderId;
  private List<Long> profileId;
  private String title;
  private String body;
  private Boolean isRead;
  private JsonObject params;
}
