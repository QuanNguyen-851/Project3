package com.example.project3.service;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import com.example.project3.model.entity.NewBillResponse;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.model.entity.TurnoverEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.Date;
import java.util.List;

public interface BillService {

  List<BillDTO> getAll(Long profileId,String phone,String status,String type, Date startDate,Date endDate, String code,String imei);

  BillDTO getById(Long billId);

  ResponseWrapper create(BillDTO billDTO);

  ResponseWrapper createByUser(BillDTO billDTO);

  ResponseWrapper updateStatus(Long billId, String status, String reason);

  NewBillResponse countNewBill(String status , String type);

  TurnoverEntity getTurnover(String status , String type, String month);

  List<TopEmployee> getTopEmployee(Long limit, String profileRole);
}
