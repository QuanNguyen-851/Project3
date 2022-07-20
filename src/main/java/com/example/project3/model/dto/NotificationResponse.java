package com.example.project3.model.dto;

import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class NotificationResponse {
  private Long id;
  private Long senderId;
  private Long profileId;
  private String title;
  private String body;
  private Boolean isRead;
  private JsonObject params;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
}
