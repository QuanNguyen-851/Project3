package com.example.project3.service.impl;

import com.example.project3.Common.Maper;
import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ProductEntity;
//import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.model.entity.ProductInformationEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.Producttest;
//import com.example.project3.repository.CategoryRepository;
import com.example.project3.repository.CategoryRepository;
import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.repository.ProductRepository;
//import com.example.project3.repository.ProductionRepository;
import com.example.project3.repository.ProductionRepository;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductInformationRepository productInformationRepository;
  @Autowired
  private ProductRepository repository;
  //  @Autowired
//  private ProductRepositoryCustom repositoryCustom;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ProductionRepository productionRepository;

  @Override
  public Page<ProductResponse> getAll(int page, int limit) {
    Pageable pageable = PageRequest.of(page, limit);
//    return repository.getAllProduct(pageable)
//        .map(value ->
//            Maper.getInstance().ProductEntityToDTO(value))
//        .map(value -> {
//          value.setCategory(categoryRepository.getById(value.getCategoryId()).getName());
//          value.setDescription(productionRepository.getById(value.getProductionId()).getName());
//          return value;
//        });
//    List<ProductDTO> = repository.getAllProduct().
    return new PageImpl<>(repository.getAllProduct(), pageable, repository.getAllProduct().size());


//        return new PageImpl<>(studentPackageResponses, pageable, countResults);
  }

  @Override
  public ProductResponse getDetail(Long id) {
    var product = repository.getProductById(id);
    product.setListInformation(productInformationRepository.findAllByProductId(id));
    return product;
  }

  @Override
  public ResponseWrapper create(ProductResponse productResponse) {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(productResponse.getName());
    productEntity.setDescription(productResponse.getDescription());
    productEntity.setCategoryId(productResponse.getCategoryId());
    productEntity.setProductionId(productResponse.getProductionId());
    productEntity.setPrice(productResponse.getPrice());
    productEntity.setQuantity(productResponse.getQuantity());
    productEntity.setStatus(productResponse.getStatus());
    productEntity.setAvatarUrl(productResponse.getAvatarUrl());
    repository.save(productEntity);
     String sortName = categoryRepository.getById(productResponse.getCategoryId()).getSortName();
    ProductEntity newProduct = repository.getNewProduct();
    newProduct.setCode(sortName + newProduct.getId());
    repository.save(newProduct);
    for (ProductInformationEntity item : productResponse.getListInformation() ) {
      item.setProductId(newProduct.getId());
//      item.setCreatedDate(LocalDateTime.now());
      productInformationRepository.save(item);
    }
    return new ResponseWrapper(EnumResponse.SUCCESS, productResponse);
  }


}
