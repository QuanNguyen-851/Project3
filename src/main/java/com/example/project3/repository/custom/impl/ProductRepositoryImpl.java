package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;
  private Session session;

  @Override
  public ProductResponse getProductById(Long id) {
    StringBuilder sql = new StringBuilder();
    sql.append("select pp.*, cc.name as category, p.name as production from p_product pp\n"
        + "left join c_category cc on pp.category_id = cc.id\n"
        + "left join p_production p on p.id = pp.production_id where pp.id = :id");
    var query = entityManager.createNativeQuery(sql.toString(), ProductResponse.class);
    query.setParameter("id", id);
    ProductResponse value = (ProductResponse) query.getSingleResult();
    return value;
  }

  @Override
  public List<ProductResponse> getAllProduct(
      String status,
      String code,
      String name,
      Long idCate,
      Long idProduction,
      Boolean getAll
  ) {
    StringBuilder sql = new StringBuilder();
    sql.append("select pp.*, cc.name as category, p.name as production "
        + ",pp.created_by, pp.modified_by "
        + " from p_product pp\n"
        + "left join c_category cc on pp.category_id = cc.id\n"
        + "left join p_production p on p.id = pp.production_id\n"
        + "where pp.id>0 ");
    if (status != null) {
      sql.append("and pp.status = :status  ");
    }
    if (code != null) {
      sql.append("and pp.code = :code  ");
    }
    if (name != null) {
      sql.append("and lower(pp.name) LIKE :name ");
    }
    if (idCate != null) {
      sql.append("and cc.id = :idCate ");
    }
    if (idProduction != null) {
      sql.append("and p.id = :idProduction ");
    }
    if(getAll.equals(false)) {
      sql.append("and cc.status != 'DISABLE' ");
      sql.append("and p.status != 'DISABLE' ");
    }
    sql.append("ORDER BY pp.id DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), ProductResponse.class);
    if (status != null) {
      query.setParameter("status", status);
    }
    if (code != null) {
      query.setParameter("code", code);
    }
    if (name != null) {
      query.setParameter("name", "%" + name.toLowerCase(Locale.ROOT) + "%");
    }
    if (idCate != null) {
      query.setParameter("idCate", idCate);
    }
    if (idProduction != null) {
      query.setParameter("idProduction", idProduction);
    }
    List<ProductResponse> list = query.getResultList();
    System.out.println(sql.toString());
    return list;
  }
}
