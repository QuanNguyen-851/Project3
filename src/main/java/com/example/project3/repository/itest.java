package com.example.project3.repository;

import com.example.project3.model.entity.testEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface itest extends JpaRepository<testEntity, Long> {

  @Query(value = "select * from t_test", nativeQuery = true)
  List<testEntity> getAll();
  Long countAllByName(String name);
}
