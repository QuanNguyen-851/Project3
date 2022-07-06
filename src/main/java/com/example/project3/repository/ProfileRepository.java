package com.example.project3.repository;

import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import com.example.project3.repository.custom.ProfileRepositoryCustom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>, ProfileRepositoryCustom {

  ProfileEntity findFirstById(Long id);

  ProfileEntity findFirstByPhone(String phone);

  ProfileEntity findFirstByEmail(String email);

  @Query(value = "select * from p_profile ORDER BY id DESC limit 1", nativeQuery = true)
  ProfileEntity findNewUser();

  ProfileEntity findFirstByPhoneAndPassWord(String phone, String password);
}
