package com.example.project3.repository.custom;

import com.example.project3.model.entity.BillEntity;
import com.example.project3.repository.BillRepository;
import java.util.List;

public interface BillRepositoryCustom {

  List<BillEntity> getAll(Long profileId,String phone,String status,String type);

}
