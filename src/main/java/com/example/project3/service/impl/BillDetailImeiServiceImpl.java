package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.BillDetailImeiRequest;
import com.example.project3.model.entity.BillDetailImeiEntity;
import com.example.project3.repository.BillDetailImeiRepository;
import com.example.project3.repository.BillDetailRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.BillDetailImeiService;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailImeiServiceImpl implements BillDetailImeiService {

  @Autowired
  private BillDetailRepository billDetailRepository;

  @Autowired
  private Token token;

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private BillDetailImeiRepository repository;

  @Override
  public ResponseWrapper create(BillDetailImeiRequest request) {
   if( token.isAnonymous()){
     return new ResponseWrapper(EnumResponse.ACCESSDENIED,null, "access required " );
   }
   var detail= billDetailRepository.findFirstById(request.getBillDetailId());
       if(detail==null){
         return new ResponseWrapper(EnumResponse.NOT_FOUND,request.getBillDetailId());
       }
       if(request.getImei().size() < detail.getQuantity()){
         return new ResponseWrapper(EnumResponse.FAIL,null, String.format("số lượng sản phẩm trong đơn hàng là %d !", detail.getQuantity()));
       }
       var profile = profileRepository.findFirstById(Long.parseLong(token.sub("id")));
       if(profile==null){
         return new ResponseWrapper(EnumResponse.NOT_FOUND,token.sub("id"), "token error !");
       }
      var val =  request.getImei().stream()
           .map(imeiString-> repository.save(
           BillDetailImeiEntity.builder()
               .billDetailId(request.getBillDetailId())
               .imei(imeiString)
               .createdBy(profile.getId())
               .createdDate(LocalDateTime.now())
               .modifiedBy(profile.getId())
               .modifiedDate(LocalDateTime.now())
               .build())).collect(Collectors.toList());
    return new ResponseWrapper(EnumResponse.SUCCESS, val);

  }
}
