package com.example.project3.service.impl;

import com.example.project3.Common.FormatDate;
import com.example.project3.model.entity.ProductSoldEntity;
import com.example.project3.model.entity.ProductSoldResponse;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProductSoldRepository;
import com.example.project3.service.ProdSoldService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdSoldServiceImpl implements ProdSoldService {

  @Autowired
  private ProductSoldRepository repository;

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Void saveProdSold(Long prodId, Long sold) {
    var thisMonth = FormatDate.getThisMonth();
    var exist = repository.getProdSold(prodId, thisMonth);
    if (exist != null) {
      ProductSoldEntity entity = new ProductSoldEntity(
      );
      entity.setId(exist.getId());
      entity.setProductId(exist.getProductId());
      entity.setDate(exist.getDate());
      entity.setSold(sold + exist.getSold());
      entity.setCreatedDate(exist.getCreatedDate());
      entity.setModifiedDate(LocalDateTime.now());
      repository.save(entity);
      //có rồi update
    } else {
      //chưa có tạo mới
      ProductSoldEntity entity = new ProductSoldEntity(
      );
      entity.setProductId(prodId);
      entity.setDate(FormatDate.convertToDate(LocalDateTime.now()));
      entity.setSold(sold);
      entity.setCreatedDate(LocalDateTime.now());
      entity.setModifiedDate(LocalDateTime.now());
      repository.save(entity);
    }
    return null;
  }

  @Override
  public List<ProductSoldResponse> listProductSold(Long productId, String month, Long limit) {
    return repository.listProductSold(productId, month, limit);
  }

  @Override
  public Long countProdSold(String month) {
    Long count =0L;
    var list = this.listProductSold(null, month, null);
    for (ProductSoldResponse prodSold: list) {
      count += prodSold.getSold();
    }
    return count;
  }
}
