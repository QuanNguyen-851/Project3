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
@Table(name = "bd_bill_detail_imei")
@NoArgsConstructor
@AllArgsConstructor
public class BillDetailImeiEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long billDetailId;
  private String imei;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private Long createdBy;
  private Long modifiedBy;
}
