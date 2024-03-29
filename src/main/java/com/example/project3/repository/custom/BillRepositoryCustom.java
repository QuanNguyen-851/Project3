package com.example.project3.repository.custom;

import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.repository.BillRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BillRepositoryCustom {

  List<BillEntity> getAll(Long profileId,String phone,String status,String type, LocalDateTime startDate, LocalDateTime endDate, String code, String imei);
  Long countByStatusAndMonth(String status,String type, String month);

  List<BillEntity> getNewBill(String status,String type, String month);

  List<TopEmployee> getTopEmployee(Long limit, String thismonth, String profileRole);

  Long countByDay(String day);
}
