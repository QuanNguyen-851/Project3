package com.example.project3.repository;

import com.example.project3.model.entity.BillDetailEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Long> {

  List<BillDetailEntity> findAllByBillId(Long billId);

  BillDetailEntity findFirstById(Long id);

}
