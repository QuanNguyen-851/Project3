package com.example.project3.repository;

import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.repository.custom.VoucherRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long>, VoucherRepositoryCustom {
  VoucherEntity findFirstByKey(String key);
  VoucherEntity findFirstById(Long id);
}
