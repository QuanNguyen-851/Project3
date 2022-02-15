package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProductSoldEntity;
import com.example.project3.model.entity.ProductSoldResponse;
import com.example.project3.repository.custom.ProductSoldRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProductSoldRepositoryImpl implements ProductSoldRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public ProductSoldEntity getProdSold(Long prodId, String month) {
    var sql = new StringBuilder();
    sql.append(" select * from ps_product_sold ps ");
    sql.append("where ps.product_id = :prodId ");
    sql.append("and to_char(ps.created_date, 'MM-YYYY') = :month ");
    sql.append("order by ps.sold DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), ProductSoldEntity.class);
    query.setParameter("prodId", prodId);
    query.setParameter("month", month);
    try {
      return (query != null) ? (ProductSoldEntity) query.getSingleResult() : null;

    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<ProductSoldResponse> listProductSold(Long prodId, String month, Long limit) {
    var sql = new StringBuilder();
    sql.append("select ps.*, pp.code, pp.name as product_name,"
        + " pp.quantity,pp.sale_price, pp.status, pp.avatar_url ");
    sql.append("from ps_product_sold ps ");
    sql.append("left join p_product pp on pp.id = ps.product_id ");
    sql.append("where pp.id >0 ");
    if (prodId != null) {
      sql.append("and ps.product_id = :prodId ");
    }
    if (month != null) {
      sql.append("and to_char(ps.created_date, 'MM-YYYY') = :month ");
    }
    sql.append("order by ps.sold DESC ");
    if (limit != null) {
      sql.append("limit :limit ");
    }
    var query = entityManager.createNativeQuery(sql.toString(), ProductSoldResponse.class);
    if (prodId != null) {
      query.setParameter("prodId", prodId);
    }
    if (month != null) {
      query.setParameter("month", month);
    }
    if(limit !=null){
      query.setParameter("limit", limit);
    }
    try {
      List<ProductSoldResponse> list = query.getResultList();
      return list;
    } catch (Exception e) {
      return null;
    }
  }
}
