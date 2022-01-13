package com.example.project3.service;

import com.example.project3.model.dto.ProductInformation;
import com.example.project3.model.entity.ProductInformationEntity;
import com.example.project3.response.ResponseWrapper;
import java.util.List;

public interface ProductInformationService {
ProductInformationEntity deleteInfor(Long inforId);
}
