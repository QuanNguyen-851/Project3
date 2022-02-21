package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.ImportProductResponse;
import com.example.project3.model.entity.SaleResponse;
import com.example.project3.repository.SaleRepository;
import com.example.project3.repository.custom.SaleRepositoryCustom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SaleRepositoryImpl implements SaleRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;
  @Override
  public List<SaleResponse> getAllProdSale(Float sale, Boolean inUse) {

    var sql =  new StringBuilder();
    sql.append("select s.*, p.code, p.name as product_name, p.avatar_url, pf.fist_name, pf.last_name from s_sale s");
    sql.append(" left join p_product p on p.id = s.product_id ");
    sql.append("left join p_profile pf on pf.id = s.created_by ");
    sql.append("where s.id >0 ");
    if(sale!=null) {
      sql.append("and sale = :sale ");
    }
    if(inUse == null){
    } else if(inUse) {
      sql.append("and (s.start_date < Now() and Now() < s.end_date) ");
    }else{
      sql.append("and ( Now() < s.start_date or Now() > s.end_date) ");
    }
    sql.append("order By s.id DESC \n");
    var query = entityManager.createNativeQuery(sql.toString(), SaleResponse.class);
    if(sale!=null) {
      query.setParameter("sale", sale);
    }
    try{
      List<SaleResponse> list = query.getResultList();
      return list;
    }catch (Exception e){
      return null;
    }
  }

  @Override
  public SaleResponse getDetail(Long saleId, Long productId) {
    var sql =  new StringBuilder();
    sql.append("select s.*, p.code, p.name as product_name, p.avatar_url, pf.fist_name, pf.last_name from s_sale s");
    sql.append(" left join p_product p on p.id = s.product_id ");
    sql.append("left join p_profile pf on pf.id = s.created_by ");
    sql.append("where s.id >0 ");
    if(saleId!=null) {
      sql.append("and s.id = :saleId");
    }
    if(productId!=null) {
      sql.append("and s.product_id = :productId ");
    }
    var query = entityManager.createNativeQuery(sql.toString(), SaleResponse.class);
    if(saleId!=null) {
      query.setParameter("saleId",saleId);
    }
    if(productId!=null) {
      query.setParameter("productId",productId);
    }
    try{
      return (SaleResponse) query.getSingleResult();
    }catch (Exception e){
      return null;
    }
  }
}
