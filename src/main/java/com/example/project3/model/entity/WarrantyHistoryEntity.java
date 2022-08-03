package com.example.project3.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name ="p_warranty_history")
@AllArgsConstructor
@NoArgsConstructor
public class WarrantyHistoryEntity {

  public enum WarrantyHistoryStatus{
    INPROGRESS,
    RESOLVED
  }
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String imei;
  private Long productId;
  private Long userId;
  private String productCondition;
  private String status;
  private Long surcharge;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
}
