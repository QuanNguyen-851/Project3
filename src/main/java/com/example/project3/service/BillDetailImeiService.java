package com.example.project3.service;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.BillDetailImeiRequest;
import com.example.project3.response.ResponseWrapper;

public interface BillDetailImeiService {

  ResponseWrapper create(BillDetailImeiRequest request);

  Boolean isExistEmei(String imei);
}
