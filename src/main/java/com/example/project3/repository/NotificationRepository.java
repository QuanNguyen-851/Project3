package com.example.project3.repository;

import com.example.project3.model.entity.NotificationEntity;
import com.example.project3.repository.custom.NotificationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>, NotificationRepositoryCustom {

  NotificationEntity findFirstById(Long id);

  Long countAllByProfileIdAndIsRead(Long profileId, Boolean isRead);

}
