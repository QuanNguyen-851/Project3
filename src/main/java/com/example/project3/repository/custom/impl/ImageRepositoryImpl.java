package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.repository.custom.ImageRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

public class ImageRepositoryImpl implements ImageRepositoryCustom {
  @PersistenceContext
  private EntityManager entityManager;
  private Session session;

  @Override
  public List<ImageEntity> getProductImageByIdProduct(Long id) {
    StringBuilder sql = new StringBuilder();
    sql.append("select ii.* from i_image ii \n"
        + "    left join pi_product_image pi on ii.id = pi.image_id\n"
        + "where pi.owner_id = :id");
    var query = entityManager.createNativeQuery(sql.toString(), ImageEntity.class);
    query.setParameter("id", id);
    List<ImageEntity> value =  query.getResultList();
    return value;
  }
}
