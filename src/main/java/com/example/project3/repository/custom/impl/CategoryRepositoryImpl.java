package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.repository.custom.CategoryRepositoryCustom;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;
  private Session session;

  @Override
  public List<CategoryEntity> getAll(String status, String name) {
    StringBuilder sql = new StringBuilder();
    sql.append("select cc.* from c_category cc \n"
        + "where\n"
        + "id > 0 ");
    if (status != null) {
      sql.append("and status = :status\n");
    }
    if (name != null) {
      sql.append("and lower(name) LIKE :name");
    }
    sql.append(" ORDER BY cc.id DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), CategoryEntity.class);
    if (status != null) {
      query.setParameter("status", status);
    }
    if (name != null) {
      query.setParameter("name", "%"+name.toLowerCase(Locale.ROOT)+"%");
    }
    List<CategoryEntity> list = query.getResultList();
    return list;
  }

  @Override
  public List<CategoryEntity> getALlCateHasProd() {
    StringBuilder sql = new StringBuilder();
    sql.append("select distinct cc.* from p_product pp ");
    sql.append("inner join c_category cc on cc.id = pp.category_id ");
    sql.append("where pp.status = 'ACTIVE'");
    sql.append(" order by cc.name ASC , cc.id DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), CategoryEntity.class);
    List<CategoryEntity> list = query.getResultList();
    return list;
  }
}
