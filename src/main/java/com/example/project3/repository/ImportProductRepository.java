package com.example.project3.repository;

import com.example.project3.model.entity.ImportProductEntity;
import com.example.project3.model.entity.VoucherEntity;
import com.example.project3.repository.custom.ImportProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportProductRepository extends JpaRepository<ImportProductEntity, Long>, ImportProductRepositoryCustom {

}
