package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import java.util.List;
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
  public List<ProductResponse> getAllProduct() {
    StringBuilder sql = new StringBuilder();
    sql.append("select pp.*, cc.name as category, p.name as production from p_product pp\n"
        + "left join c_category cc on pp.category_id = cc.id\n"
        + "left join p_production p on p.id = pp.production_id");
    var query = entityManager.createNativeQuery(sql.toString(), ProductResponse.class);
    List<ProductResponse> list = query.getResultList();
    return list;
  }
}
