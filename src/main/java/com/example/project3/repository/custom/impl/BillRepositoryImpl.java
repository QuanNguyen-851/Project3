package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.BillEntity;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.custom.BillRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BillRepositoryImpl implements BillRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<BillEntity> getAll(Long profileId, String phone, String status, String type) {
    var sql = new StringBuilder();
    sql.append("select * from b_bill \n"
        + "where id > 0\n");
    if (profileId != null) {
         sql.append("and profile_id = :profileId\n") ;
    }
    if(phone!=null){
      sql.append("and phone = :phone\n");
    }
    if(status!=null){
      sql.append("and status = :status\n");
    }
    if(type!=null){
      sql.append("and type = :type\n");
    }
    sql.append("ORDER BY id DESC");
    var query = entityManager.createNativeQuery(sql.toString(), BillEntity.class);
    if(profileId!=null){
      query.setParameter("profileId", profileId);
    }
    if(status!=null){
    query.setParameter("status", status);
    }
    if(phone!=null){
    query.setParameter("phone", phone);
    }
    if(type!=null){
    query.setParameter("type", type);
    }
    List<BillEntity> list = query.getResultList();
    return list;
  }
}
