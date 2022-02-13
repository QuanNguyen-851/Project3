package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import com.example.project3.repository.custom.ProfileRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProfileRepositoryImpl implements ProfileRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Long countNewProfile(String role, String month) {
    var sql = new StringBuilder();
    sql.append("select count(*) from p_profile "
        + "where to_char(created_date, 'MM-YYYY') = :month ");
    if (role != null) {
      sql.append("and role = :role ");
    }
    var query = entityManager.createNativeQuery(sql.toString());
    query.setParameter("month", month);
    if(role != null){
      query.setParameter("role", role);
    }
    return Long.parseLong(query.getSingleResult().toString());
  }

  @Override
  public List<ProfileEntity> getNewProfile(String role, String month, Long limit) {
    var sql = new StringBuilder();
    sql.append("select * from p_profile "
        + "where to_char(created_date, 'MM-YYYY') = :month ");
    if (role != null) {
      sql.append("and role = :role ");
    }
    sql.append("ORDER BY id DESC ");
    if(limit !=null) {
      sql.append("LIMIT :limit");
    }
//    System.out.println( sql);
    var query = entityManager.createNativeQuery(sql.toString(), ProfileEntity.class);
    query.setParameter("month", month);
    if(role != null){
      query.setParameter("role", role);
    }
    if(limit !=null) {
      query.setParameter("limit", limit);
    }
    return query.getResultList();
  }
}
