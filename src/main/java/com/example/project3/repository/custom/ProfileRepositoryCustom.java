package com.example.project3.repository.custom;

import com.example.project3.model.entity.ProfileEntity;
import java.util.List;

public interface ProfileRepositoryCustom {

  Long countNewProfile(String role, String month);

  List<ProfileEntity> getNewProfile(String role, String month, Long limit);
}
