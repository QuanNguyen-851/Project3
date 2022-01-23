package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.entity.ProductionEntity;
import com.example.project3.repository.custom.ProductionRepositoryCustom;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProductionRepositoryImpl implements ProductionRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<ProductionEntity> getAll(String status, String name) {
    StringBuilder sql = new StringBuilder();
    sql.append("select pp.* from p_production pp \n"
        + "where\n"
        + "pp.id > 0 ");
    if (status != null) {
      sql.append("and status = :status\n");
    }
    if (name != null) {
      sql.append("and lower(name) LIKE :name");
    }
    sql.append(" ORDER BY pp.id DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), ProductionEntity.class);
    if (status != null) {
      query.setParameter("status", status);
    }
    if (name != null) {
      query.setParameter("name", "%" + name.toLowerCase(Locale.ROOT) + "%");
    }
    List<ProductionEntity> list = query.getResultList();
    return list;
  }
}

