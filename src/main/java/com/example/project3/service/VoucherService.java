package com.example.project3.service;

import com.example.project3.model.dto.VoucherDTO;
import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface VoucherService {

  List<VoucherEntity> getAll(String name, String key, Boolean isPercent );

  ResponseWrapper create(VoucherDTO saleEntity);

  ResponseWrapper update(VoucherDTO saleEntity);

  ResponseWrapper delete(Long id);

}
