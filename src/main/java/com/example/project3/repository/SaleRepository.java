package com.example.project3.repository;

import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository  extends JpaRepository<SaleEntity, Long> {
}