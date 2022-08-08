package com.example.project3.repository.custom.impl;

import com.example.project3.model.dto.NotificationResponse;
import com.example.project3.model.entity.NotificationEntity;
import com.example.project3.repository.custom.NotificationRepositoryCustom;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.transform.AliasToBeanResultTransformer;


public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<NotificationEntity> getNotification(Boolean isRead, Long profileId) {
    StringBuilder sql = new StringBuilder();
    sql.append("select n.* from n_notification n ");
    sql.append("left join p_profile pp on pp.id = n.profile_id ");
    sql.append("where n.profile_id = :profileId ");
    if (isRead != null) {
      sql.append("and is_read = :isRead ");
    }
    sql.append("order by n.id DESC ");
    var query = entityManager.createNativeQuery(sql.toString(), NotificationEntity.class);

    query.setParameter("profileId", profileId);
    if (isRead != null) {
      query.setParameter("isRead", isRead);
    }

    var value =  query
        .getResultList();
    return value;
  }

}

