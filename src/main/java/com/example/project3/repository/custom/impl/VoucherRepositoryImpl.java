package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.repository.custom.VoucherRepositoryCustom;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class VoucherRepositoryImpl implements VoucherRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<VoucherEntity> getall(String name, String key, Boolean isPercent) {
    StringBuilder sql = new StringBuilder();
    sql.append("select v.* from v_voucher v \n"
        + "where v.id>0 ");
    if (key != null) {
      sql.append("and v.key = :key \n");
    }
    if (isPercent != null) {
      sql.append("and is_percent = :isPercent \n");
    }
    if (name != null) {
      sql.append("and lower(name) LIKE :name");
    }
    sql.append(" order by v.id ESC ");
    var query = entityManager.createNativeQuery(sql.toString(), VoucherEntity.class);
    if (key != null) {
      query.setParameter("key", key);
    }
    if (name != null) {
      query.setParameter("name", "%" + name.toLowerCase(Locale.ROOT) + "%");
    }
    if (isPercent != null) {
      query.setParameter("isPercent", isPercent);
    }
    List<VoucherEntity> list = query.getResultList();
    return list;
  }
}

