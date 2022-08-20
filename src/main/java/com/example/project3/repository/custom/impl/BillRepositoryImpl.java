package com.example.project3.repository.custom.impl;

import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.BillEntity.BillStatusEnum;
import com.example.project3.model.entity.TopEmployee;
import com.example.project3.repository.BillRepository;
import com.example.project3.repository.custom.BillRepositoryCustom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.thymeleaf.util.StringUtils;

public class BillRepositoryImpl implements BillRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<BillEntity> getAll(Long profileId, String phone, String status,
      String type, LocalDateTime startDate, LocalDateTime endDate, String code, String imei) {
    var sql = new StringBuilder();
    sql.append("select * from b_bill b \n");
    if(!StringUtils.isEmpty(imei)){
      sql.append("inner join bd_bill_detail bd on bd.bill_id = b.id ");
      sql.append("inner join bd_bill_detail_imei bdi on bd.id = bdi.bill_detail_id ");
    }
    sql.append("where b.id > 0\n");
    if(!StringUtils.isEmpty(imei)){
      sql.append("and bdi.imei = :imei ");
    }
    if (profileId != null) {
      sql.append("and b.profile_id = :profileId\n");
    }
    if (phone != null) {
      sql.append("and b.phone = :phone\n");
    }
    if (status != null) {
      sql.append("and b.status = :status\n");
    }
    if (type != null) {
      sql.append("and b.type = :type\n");
    }
    if (code != null) {
      sql.append("and b.code = :code\n");
    }
    sql.append(" and (b.created_date <= :endDate  and b.created_date>= :startDate ) ");
    if (type != null &&  type.equals(BillStatusEnum.VERIFYING.toString())) {
      sql.append("ORDER BY b.total_price DESC, b.id ASC ");
    }else{
      sql.append("ORDER BY b.id DESC");
    }
    var query = entityManager.createNativeQuery(sql.toString(), BillEntity.class);

    if (profileId != null) {
      query.setParameter("profileId", profileId);
    }
    if (status != null) {
      query.setParameter("status", status);
    }
    if (phone != null) {
      query.setParameter("phone", phone);
    }
    if (type != null) {
      query.setParameter("type", type);
    }
    if (code != null) {
      query.setParameter("code", code);
    }
    if(!StringUtils.isEmpty(imei)){
      query.setParameter("imei", imei);
    }
    query.setParameter("endDate", endDate);
    query.setParameter("startDate", startDate);
    List<BillEntity> list = query.getResultList();
    return list;
  }

  @Override
  public Long countByStatusAndMonth(String status, String type, String month) {
    var sql = new StringBuilder();
    sql.append("select count(*) "
        + "from b_bill where id>0 ");
    if (month != null) {
      sql.append("and to_char(created_date, 'MM-YYYY') = :month ");
    }
    if (status != null) {
      sql.append("and status = :status ");
    }
    if (type != null) {
      sql.append("and type = :type ");
    }
    var query = entityManager.createNativeQuery(sql.toString());
    if (status != null) {
      query.setParameter("status", status);
    }
    if (type != null) {
      query.setParameter("type", type);
    }
    if (month != null) {
      query.setParameter("month", month);
    }
    return Long.parseLong(query.getSingleResult().toString());
  }

  @Override
  public List<BillEntity> getNewBill(String status, String type, String month) {
    var sql = new StringBuilder();
    sql.append("select * "
        + "from b_bill where ");
    if (month.startsWith("00")) {
      month = month.substring(month.lastIndexOf("-") + 1);
      sql.append("to_char(created_date, 'YYYY') = :month ");
    } else {
      sql.append("to_char(created_date, 'MM-YYYY') = :month ");
    }
    if (status != null) {
      sql.append("and status = :status ");
    }
    if (type != null) {
      sql.append("and type = :type ");
    }
    sql.append(" ORDER BY id DESC");
    var query = entityManager.createNativeQuery(sql.toString(), BillEntity.class);
    if (status != null) {
      query.setParameter("status", status);
    }
    if (type != null) {
      query.setParameter("type", type);
    }
    query.setParameter("month", month);
    return query.getResultList();
  }

  @Override
  public List<TopEmployee> getTopEmployee(Long limit, String thisMonth, String profileRole) {
    var sql = new StringBuilder();
    sql.append("select distinct(b.profile_id), count(b.id) as bill_count\n"
        + "from  b_bill b\n"
        + "left join  p_profile p on p.id = b.profile_id\n"
        + "where to_char(b.created_date, 'MM-YYYY') = :thisMonth \n");
    if (profileRole != null) {
      sql.append("and p.role = :profileRole\n");
    }
    sql.append("group by profile_id\n");
    sql.append("ORDER BY bill_count DESC");
    if (limit != null) {
      sql.append(" limit :limit ");
    }
    var query = entityManager.createNativeQuery(sql.toString(), TopEmployee.class);
    if (profileRole != null) {
      query.setParameter("profileRole", profileRole);
    }
    if (limit != null) {
      query.setParameter("limit", limit);
    }
    query.setParameter("thisMonth", thisMonth);
    return query.getResultList();
  }

  @Override
  public Long countByDay(String day) {
    var sql = new StringBuilder();
    sql.append("select count(*) "
        + "from b_bill where id>0 ");
    if (day != null) {
      sql.append("and to_char(created_date, 'dd-MM-YYYY') = :day ");
    }
    var query = entityManager.createNativeQuery(sql.toString());
    query.setParameter("day", day);
    return Long.parseLong(query.getSingleResult().toString());
  }
}
