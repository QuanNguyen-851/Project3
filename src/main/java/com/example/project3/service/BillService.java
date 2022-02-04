package com.example.project3.service;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface BillService {

  List<BillDTO> getAll(Long profileId,String phone,String status,String type);

  BillDTO getByProfileId(Long profileId);

  ResponseWrapper create(BillDTO billDTO);
}
