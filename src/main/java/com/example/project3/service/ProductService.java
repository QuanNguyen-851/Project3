package com.example.project3.service;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  Page<ProductDTO> getAll(int page, int size);

}
