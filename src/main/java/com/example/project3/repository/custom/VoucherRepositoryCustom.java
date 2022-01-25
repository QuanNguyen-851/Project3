package com.example.project3.repository.custom;

import com.example.project3.model.entity.VoucherEntity;
import java.util.List;

public interface VoucherRepositoryCustom {
  List<VoucherEntity> getall(String name, String key, Boolean isPercent);

}
