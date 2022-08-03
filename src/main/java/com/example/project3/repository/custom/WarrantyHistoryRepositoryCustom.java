package com.example.project3.repository.custom;

import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

public interface WarrantyHistoryRepositoryCustom {

  List<WarrantyHistoryEntity> getListHistoryEntity(String searchKey, Long profileId,  WarrantyHistoryStatus status);
}
