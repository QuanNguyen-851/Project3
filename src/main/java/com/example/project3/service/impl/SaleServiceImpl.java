package com.example.project3.service.impl;

import com.example.project3.model.dto.SaleDTO;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.repository.SaleRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.SaleService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

  @Autowired
  private SaleRepository repository;

  @Override
  public List<SaleEntity> getAll(String name, String key, Boolean isPercent) {
    return repository.getall(name, key, isPercent);
  }

  @Override
  public ResponseWrapper create(SaleDTO saleDTO) {
    var key = repository.findFirstByKey(saleDTO.getKey());
    if (saleDTO.getIsPercent() != null && saleDTO.getKey() != null) {
      if (key != null) {
        var err = EnumResponse.EXIST;
        err.setResponseMessage("Key này đã tồn tại");
        return new ResponseWrapper(EnumResponse.EXIST, key);
      }
      SaleEntity saleEntity = new SaleEntity();
      saleEntity.setCreatedDate(LocalDateTime.now());
      saleEntity.setDiscountPrice(saleDTO.getDiscountPrice());
      saleEntity.setPercentage(saleDTO.getPercentage());
      saleEntity.setIsPercent(saleDTO.getIsPercent());
      var start = saleDTO.getStartDate();
      var end = saleDTO.getEndDate();
      saleEntity.setStartDate(start.atStartOfDay());
      saleEntity.setEndDate(end.atTime(23, 59, 59));
      repository.save(saleEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, saleDTO);
    } else {
      var err = EnumResponse.FAIL;
      err.setResponseMessage("ispercent, key không được để trống! true/false");
      return new ResponseWrapper(EnumResponse.FAIL, null);
    }
  }

  @Override
  public ResponseWrapper update(SaleDTO saleDTO) {
    var key = repository.findFirstByKey(saleDTO.getKey());
    if (saleDTO.getIsPercent() != null && saleDTO.getId() != null && saleDTO.getKey() != null) {
      if (key != null) {
        var err = EnumResponse.EXIST;
        err.setResponseMessage("Key này đã tồn tại");
        return new ResponseWrapper(EnumResponse.EXIST, key);
      }
      var saleExist = repository.getById(saleDTO.getId());
      if(saleExist ==null){
        return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
      }
      SaleEntity saleEntity = new SaleEntity();
      saleEntity.setId(saleDTO.getId());
      saleEntity.setName(saleDTO.getName());
      saleEntity.setKey(saleDTO.getKey());
      saleEntity.setCreatedDate(saleExist.getCreatedDate());
      saleEntity.setModifiedDate(LocalDateTime.now());
      saleEntity.setDiscountPrice(saleDTO.getDiscountPrice());
      saleEntity.setPercentage(saleDTO.getPercentage());
      saleEntity.setIsPercent(saleDTO.getIsPercent());
      var start = saleDTO.getStartDate();
      var end = saleDTO.getEndDate();
      saleEntity.setStartDate(start.atStartOfDay());
      saleEntity.setEndDate(end.atTime(23, 59, 59));
      repository.save(saleEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, saleDTO);
    } else {
      var err = EnumResponse.FAIL;
      err.setResponseMessage("ispercent và id không được để trống! true/false");
      return new ResponseWrapper(EnumResponse.FAIL, null);
    }
  }

  @Override
  public ResponseWrapper delete(Long id) {
    var sale = repository.findFirstById(id);
    if(sale!=null){
      repository.deleteById(id);
      return new ResponseWrapper(EnumResponse.SUCCESS, sale);
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }
}
