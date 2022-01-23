package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.repository.custom.SaleRepositoryCustom;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SaleRepositoryImpl implements SaleRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<SaleEntity> getall(String name, String key, Boolean isPercent) {
    StringBuilder sql = new StringBuilder();
    sql.append("select s.* from s_sale s \n"
        + "where s.id>0 ");
    if (key != null) {
      sql.append("and s.key = :key \n");
    }
    if (isPercent != null) {
      sql.append("and is_percent = :isPercent \n");
    }
    if (name != null) {
      sql.append("and lower(name) LIKE :name");
    }
    sql.append(" order by s.key Asc ");
    var query = entityManager.createNativeQuery(sql.toString(), SaleEntity.class);
    if (key != null) {
      query.setParameter("key", key);
    }
    if (name != null) {
      query.setParameter("name", "%" + name.toLowerCase(Locale.ROOT) + "%");
    }
    if (isPercent != null) {
      query.setParameter("isPercent", isPercent);
    }
    List<SaleEntity> list = query.getResultList();
    return list;
  }
}

