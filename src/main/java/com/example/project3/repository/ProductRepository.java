package com.example.project3.repository;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductionEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  @Query(value = "select pp.* "
      + ", cc.name as category "
      + ", p.name as production "
      + "from p_product pp "
      + "left join c_category cc on pp.category_id = cc.id "
      + "left join p_production p on p.id = pp.production_id ", nativeQuery = true)
  Page<ProductEntity> getAllProduct(Pageable pageable);

}
