package com.example.project3.repository;

import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.BillDetailImeiEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailImeiRepository extends JpaRepository<BillDetailImeiEntity, Long> {

  List<BillDetailImeiEntity> findAllByBillDetailIdOrderByIdAsc(Long billDetailId);
}
