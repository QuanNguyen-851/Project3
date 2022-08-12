package com.example.project3.repository;

import com.example.project3.model.entity.BillEntity;
import com.example.project3.repository.custom.BillRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<BillEntity, Long>, BillRepositoryCustom {


  BillEntity findFirstById(Long id);
}
