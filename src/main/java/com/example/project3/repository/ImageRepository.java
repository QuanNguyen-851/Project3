package com.example.project3.repository;

import com.example.project3.model.entity.ImageEntity;
import com.example.project3.repository.custom.ImageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long>, ImageRepositoryCustom {

}
