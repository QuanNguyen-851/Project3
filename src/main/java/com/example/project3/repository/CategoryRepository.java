package com.example.project3.repository;

import com.example.project3.model.entity.CategoryEntity;
import com.example.project3.repository.custom.CategoryRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, CategoryRepositoryCustom {

  CategoryEntity findFirstById(Long id);
}
