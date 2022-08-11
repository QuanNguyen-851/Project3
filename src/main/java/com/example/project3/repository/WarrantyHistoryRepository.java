package com.example.project3.repository;

import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.repository.custom.WarrantyHistoryRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarrantyHistoryRepository extends JpaRepository<WarrantyHistoryEntity, Long>, WarrantyHistoryRepositoryCustom {


}
