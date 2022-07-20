package com.example.project3.model.entity;

import com.example.project3.model.dto.NotificationResponse;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name ="n_notification")
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

  public enum RedirectEnum{
    lIST_BILL_PAGE,
    DETAIL_PAGE
  }



  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long senderId;
  private Long profileId;
  private String title;
  private String body;
  private Boolean isRead;
  private String params;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
}
