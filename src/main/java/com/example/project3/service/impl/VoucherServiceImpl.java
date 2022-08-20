package com.example.project3.service.impl;

import com.example.project3.model.dto.VoucherDTO;
import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.repository.VoucherRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.VoucherService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherServiceImpl implements VoucherService {

  @Autowired
  private VoucherRepository repository;

  @Override
  public List<VoucherEntity> getAll(String name, String key, Boolean isPercent) {
    return repository.getall(name, key, isPercent);
  }

  @Override
  public ResponseWrapper create(VoucherDTO voucherDTO) {
    var key = repository.findFirstByKey(voucherDTO.getKey());
    if (voucherDTO.getIsPercent() != null && voucherDTO.getKey() != null) {
      if (key != null) {
        var err = EnumResponse.EXIST;
        err.setResponseMessage("Key này đã tồn tại");
        return new ResponseWrapper(EnumResponse.EXIST, key);
      }
      VoucherEntity voucherEntity = new VoucherEntity();
      voucherEntity.setKey(voucherDTO.getKey().toUpperCase(Locale.ROOT));
      voucherEntity.setName(voucherDTO.getName());
      voucherEntity.setCreatedDate(LocalDateTime.now());
      voucherEntity.setModifiedDate(LocalDateTime.now());
      voucherEntity.setDiscountPrice(voucherDTO.getDiscountPrice());
      voucherEntity.setPercentage(voucherDTO.getPercentage());
      voucherEntity.setIsPercent(voucherDTO.getIsPercent());
      if(voucherDTO.getMinPrice()==null){
        voucherEntity.setMinPrice(0L);
      }else{
        voucherEntity.setMinPrice(voucherDTO.getMinPrice());
      }
      if(voucherDTO.getQuantity()==null){
        voucherEntity.setQuantity(0L);
      }else{
        voucherEntity.setQuantity(voucherDTO.getQuantity());
      }
      var start = voucherDTO.getStartDate();
      var end = voucherDTO.getEndDate();
      voucherEntity.setStartDate(start.atStartOfDay());
      voucherEntity.setEndDate(end.atTime(23, 59, 59));
      repository.save(voucherEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, voucherDTO);
    } else {
      var err = EnumResponse.FAIL;
      err.setResponseMessage("ispercent, key không được để trống! true/false");
      return new ResponseWrapper(EnumResponse.FAIL, null);
    }
  }

  @Override
  public ResponseWrapper update(VoucherDTO voucherDTO) {
    var key = repository.findFirstByKey(voucherDTO.getKey());
    if (voucherDTO.getIsPercent() != null && voucherDTO.getId() != null && voucherDTO.getKey() != null) {
      if (key != null && !key.getId().equals(voucherDTO.getId())) {
        var err = EnumResponse.EXIST;
        err.setResponseMessage("Key này đã tồn tại");
        return new ResponseWrapper(EnumResponse.EXIST, key);
      }
      var saleExist = repository.getById(voucherDTO.getId());
      if (saleExist == null) {
        return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
      }
      VoucherEntity voucherEntity = new VoucherEntity();
      voucherEntity.setId(voucherDTO.getId());
      voucherEntity.setName(voucherDTO.getName());
      voucherEntity.setKey(voucherDTO.getKey());
      voucherEntity.setCreatedDate(saleExist.getCreatedDate());
      voucherEntity.setModifiedDate(LocalDateTime.now());
      voucherEntity.setDiscountPrice(voucherDTO.getDiscountPrice());
      voucherEntity.setPercentage(voucherDTO.getPercentage());
      voucherEntity.setIsPercent(voucherDTO.getIsPercent());
      voucherEntity.setMinPrice(voucherDTO.getMinPrice()==null?0L:voucherDTO.getMinPrice());
      voucherEntity.setQuantity(voucherDTO.getQuantity()==null?0L:voucherDTO.getQuantity());
      var start = voucherDTO.getStartDate();
      var end = voucherDTO.getEndDate();
      voucherEntity.setStartDate(start.atStartOfDay());
      voucherEntity.setEndDate(end.atTime(23, 59, 59));
      repository.save(voucherEntity);
      return new ResponseWrapper(EnumResponse.SUCCESS, voucherDTO);
    } else {
      var err = EnumResponse.FAIL;
      err.setResponseMessage("ispercent và id không được để trống! true/false");
      return new ResponseWrapper(EnumResponse.FAIL, null);
    }
  }

  @Override
  public ResponseWrapper delete(Long id) {
    var sale = repository.findFirstById(id);
    if (sale != null) {
      repository.deleteById(id);
      return new ResponseWrapper(EnumResponse.SUCCESS, sale);
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }

  @Override
  public VoucherEntity findById(Long id) {
    var item = repository.findFirstById(id);
    if (item != null) {
      return item;
    }
    return null;
  }

  @Override
  public VoucherEntity findByKey(String key) {
    var item = repository.findFirstByKey(key.toUpperCase(Locale.ROOT));
    if (item != null) {
      return item;
    }
    return null;
  }
}
