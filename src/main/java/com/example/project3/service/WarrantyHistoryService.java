package com.example.project3.service;

import com.example.project3.model.dto.WarrantyHistoryResponse;
import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import com.example.project3.response.ResponseWrapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

public interface WarrantyHistoryService {

  List<WarrantyHistoryResponse> getWarrantyHistory(String searchKey,  WarrantyHistoryStatus status);

  ResponseWrapper createWarrantyHistory(WarrantyHistoryEntity entity);

  ResponseWrapper update(WarrantyHistoryEntity entity, Long warrantyHistoryId);
}
