package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;
import com.example.project3.repository.custom.WarrantyHistoryRepositoryCustom;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.thymeleaf.util.StringUtils;

public class WarrantyHistoryRepositoryCustomImpl implements WarrantyHistoryRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;
  @Override
  public List<WarrantyHistoryEntity> getListHistoryEntity(String searchKey, Long profileId, WarrantyHistoryStatus status) {
    HashMap<String, Object> params = new HashMap<>();
    var sql = new StringBuilder();
    sql.append("select pwh.* from p_warranty_history pwh ");
    sql.append("inner join p_profile pp on pp.id = pwh.id ");
    sql.append("Where pwh.id > 0 ");
    if(!StringUtils.isEmpty(searchKey)){
      sql.append("and ( ");
      sql.append("pp.phone = :phone ");
      sql.append("or pwh.imei = :imei ");
      sql.append(" ) ");
      params.put("phone", searchKey);
      params.put("imei", searchKey);
    }
    if(profileId != null){
      sql.append("and pwh.id = :profileId ");
      params.put("profileId", profileId);
    }
    if(!Objects.isNull(status)){
      sql.append("and pwh.status = :status ");
      params.put("status", status.toString());
    }
    sql.append("Order by pwh.id DESC ");

    var query = entityManager.createNativeQuery(sql.toString(), WarrantyHistoryEntity.class);
    for (var paramKey : params.keySet()) {
      query.setParameter(paramKey, params.get(paramKey));
    }
    List<WarrantyHistoryEntity> list = query.getResultList();
    return list;
  }
}
