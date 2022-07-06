package com.example.project3.repository.custom;

import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.ProfileEntity.RoleEnum;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepositoryCustom {

  Long countNewProfile(String role, String month);

  List<ProfileEntity> getNewProfile(String role, String month, Long limit);

  List<ProfileEntity> getAllProfileByRoles(List<RoleEnum> roles);
}
