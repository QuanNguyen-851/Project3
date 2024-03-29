package com.example.project3.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
@Table(name = "p_profile")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProfileEntity {

  public enum RoleEnum {
    SUPERADMIN,
    ADMIN,
    EMPLOYEE,
    USER
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fistName;
  private String lastName;
  private String imageUrl;
  private String phone;
  private String email;
  private LocalDate dateBirth;
  private String gender;
  private String passWord;
  private String address;
  private String role;
  private Boolean block;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;
}
