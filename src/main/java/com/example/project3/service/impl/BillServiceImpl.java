package com.example.project3.service.impl;

import com.example.project3.Common.Maper;
import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.entity.BillEntity;
import com.example.project3.repository.BillRepository;
import com.example.project3.service.BillService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

  @Autowired
  private BillRepository repository;

  @Override
  public List<BillDTO> getAll(Long profileId, String phone, String status, String type) {
    List<BillDTO> res = new ArrayList<>();
    for (BillEntity bill : repository.getAll(profileId, phone, status, type)) {
      res.add(Maper.getInstance().BillEntityToBillDTO(bill));
    }
    return res;
  }
}
