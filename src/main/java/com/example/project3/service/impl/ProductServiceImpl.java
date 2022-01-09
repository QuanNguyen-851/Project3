package com.example.project3.service.impl;

import com.example.project3.Common.Maper;
import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.repository.ProductRepository;
import com.example.project3.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository repository;

  @Override
  public Page<ProductDTO> getAll(int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
    return repository.getAllProduct(pageable)
        .map(value-> Maper.getInstance().ProductEntityToDTO(value));
  }

}
