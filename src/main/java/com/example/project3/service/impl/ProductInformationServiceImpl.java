package com.example.project3.service.impl;

import com.example.project3.model.dto.ProductInformation;
import com.example.project3.model.entity.ProductInformationEntity;
import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductInformationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInformationServiceImpl implements ProductInformationService {

  @Autowired
  private ProductInformationRepository repository;

//  @Override
//  public Void addInformation(List<ProductInformationEntity> listInfor) {
//    for (ProductInformationEntity item : listInfor) {
//      if (item.getKey() != null
//          && item.getValue() != null
//          && item.getProductId() != null
//      ) {
//        if(repository.findFirstByProductIdAndKey(item.getProductId(), item.getKey()).getId()!=null){
//          return null;
//        }else{
//          repository.save(item);
//          return new ResponseWrapper(EnumResponse.EXIST, )
//        }
//      }
//    }
//    return null;
//  }

  @Override
  public ProductInformationEntity deleteInfor(Long inforId) {
    if (inforId != null && repository.existsById(inforId)) {
      var response = repository.findFirstById(inforId);
      repository.deleteById(inforId);
      return response;
    }else{
      return null;
    }
  }
}
