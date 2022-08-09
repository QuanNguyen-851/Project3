package com.example.project3.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "b_bill")
public class BillEntity {
  public enum BillStatusEnum{
    VERIFYING, // đang chờ duyệt
    VERIFIED,  // Đã xác nhận
    COMPLETED, // Đã giao hàng thành công
    INPROGRESS , // Đơn hàng đang được vận chuyển
    CANCELED,  // Đã hủy
    CANCELED_REQUEST //user yêu cầu hủy đơn hàng

  }
  public enum BillTypeEnum{
    OFFLINE,
    ONLINE,
    COD
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long profileId;
  private Long voucherId;
  private String description;
  private Long totalPrice;
  private Long DiscountPrice;
  private String owner_name;
  private String phone;
  private String email;
  private String address;
  private String status;
  private String type;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
  private String reason;
  private Long modifiedBy;
  private String code;

}
