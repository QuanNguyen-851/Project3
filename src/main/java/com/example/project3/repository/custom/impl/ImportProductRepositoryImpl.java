package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.repository.custom.ImportProductRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ImportProductRepositoryImpl implements ImportProductRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<ImportProductResponse> getByProductIdAndDate(Long prodId, String date, Long limit) {
    var sql = new StringBuilder();
    sql.append("select ip.*, p.code, p.name, p.avatar_url, p.quantity, pf.fist_name, pf.last_name ");
    sql.append("from ip_import_product ip ");
    sql.append("left join p_product p on ip.product_id =  p.id ");
    sql.append("left join p_profile pf on ip.created_by = pf.id\n"
        + "where ip.id>0 ");
    if (prodId != null) {
      sql.append("and product_id = :prodId ");
    }
    if (date != null) {
      sql.append("and to_char(ip.created_date, 'MM-YYYY') = :date ");
    }
    sql.append("Order By ip.id DESC ");
    if (limit != null) {
      sql.append("LIMIT :limit ");
    }
    var query = entityManager.createNativeQuery(sql.toString(), ImportProductResponse.class);
    if (prodId != null) {
      query.setParameter("prodId", prodId);
    }
    if (date != null) {
      query.setParameter("date", date);
    }
    if (limit != null) {
      query.setParameter("limit", limit);

    }
//    List<ImportProductResponse> list = query.getResultList();
//      return list;
    try{
      List<ImportProductResponse> list = query.getResultList();
      return list;
    }catch (Exception e){
      return null;
    }
  }
}
