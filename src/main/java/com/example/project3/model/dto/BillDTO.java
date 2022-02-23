package com.example.project3.model.dto;

import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.ProfileEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BillDTO {
  private Long id;
  private Long profileId;
  private Long voucherId;
  private String description;
  private Long totalPrice;
  private Long discountPrice;
  private String ownerName;
  private String phone;
  private String email;
  private String address;
  private String status;
  private String type;
  private ProfileEntity createBy;
  private List<BillDetailResponse> billDetail;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
}
