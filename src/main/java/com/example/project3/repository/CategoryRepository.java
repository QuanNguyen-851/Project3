package com.example.project3.repository;

import com.example.project3.model.DisableStatus;
import com.example.project3.model.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  CategoryEntity findFirstById(Long id);
}
