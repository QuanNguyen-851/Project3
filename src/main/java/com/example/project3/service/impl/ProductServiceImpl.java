package com.example.project3.service.impl;

import com.example.project3.Common.Maper;
import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ImageEntity;
import com.example.project3.model.entity.ProductEntity;
//import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.model.entity.ProductEntity.ProductEnum;
import com.example.project3.model.entity.ProductImageEntity;
import com.example.project3.model.entity.ProductInformationEntity;
import com.example.project3.model.entity.ProductResponse;
//import com.example.project3.repository.CategoryRepository;
import com.example.project3.model.enumpk.ImageType;
import com.example.project3.repository.CategoryRepository;
import com.example.project3.repository.ImageRepository;
import com.example.project3.repository.ProductImageRepository;
import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.repository.ProductRepository;
//import com.example.project3.repository.ProductionRepository;
import com.example.project3.repository.ProductionRepository;
import com.example.project3.repository.custom.ProductRepositoryCustom;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ImageService;
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
    @Autowired
  private ProductImageRepository productImageRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private ProductionRepository productionRepository;
  @Autowired
  private ImageRepository imageRepository;

  @Override
  public List<ProductResponse> getAll() {

//    Pageable pageable = PageRequest.of(page, limit);
//    return repository.getAllProduct(pageable)
//        .map(value ->
//            Maper.getInstance().ProductEntityToDTO(value))
//        .map(value -> {
//          value.setCategory(categoryRepository.getById(value.getCategoryId()).getName());
//          value.setDescription(productionRepository.getById(value.getProductionId()).getName());
//          return value;
//        });
//    List<ProductDTO> = repository.getAllProduct().
    return repository.getAllProduct();
//    return new PageImpl<>(repository.getAllProduct(), pageable, repository.getAllProduct().size());


//        return new PageImpl<>(studentPackageResponses, pageable, countResults);
  }

  @Override
  public ProductResponse getDetail(Long id) {
    try {
      var product = repository.getProductById(id);
      product.setListInformation(productInformationRepository.findAllByProductId(id));
      product.setListImage(imageRepository.getProductImageByIdProduct(id));
      return product;
    }catch (Exception e){
      return new ProductResponse();
    }

  }

  @Override
  public ResponseWrapper create(ProductResponse productResponse) {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(productResponse.getName());
    productEntity.setDescription(productResponse.getDescription());
    productEntity.setCategoryId(productResponse.getCategoryId());
    productEntity.setProductionId(productResponse.getProductionId());
    productEntity.setSalePrice(productResponse.getSalePrice());
    productEntity.setImportPrice(productResponse.getImportPrice());
    productEntity.setDiscount(productResponse.getDiscount());
    productEntity.setQuantity(productResponse.getQuantity());
    productEntity.setStatus(ProductEnum.ACTIVE.name());
    productEntity.setAvatarUrl(productResponse.getAvatarUrl());
    productEntity.setCreatedDate(LocalDateTime.now());
//    repository.save(productEntity);
     String sortName = categoryRepository.getById(productResponse.getCategoryId()).getSortName();
//    ProductEntity newProduct = repository.getNewProduct();
    ProductEntity newProduct = repository.save(productEntity);
    newProduct.setCode(sortName + newProduct.getId());
    repository.save(newProduct);
    if(productResponse.getListInformation()!=null){
      this.setProductInfor(productResponse.getListInformation(), newProduct.getId());
    }
    if(productResponse.getListImage()!=null){
      this.setImage(productResponse.getListImage(), newProduct.getId());
    }
    return new ResponseWrapper(EnumResponse.SUCCESS, productResponse);
  }
  private Void setImage(List<ImageEntity>list, Long productId){
    for(ImageEntity item : list){
      item.setType(ImageType.PRODUCT.name());
      item.setCreatedDate(LocalDateTime.now());
      var newimage= imageRepository.save(item);
      ProductImageEntity pImage = new ProductImageEntity();
      pImage.setImageId(newimage.getId());
      pImage.setOwnerId(productId);
      productImageRepository.save(pImage);
    }
    return null;
  }

  private Void setProductInfor(List<ProductInformationEntity> list, Long productId){
    for (ProductInformationEntity item : list ) {
      item.setProductId(productId);
      item.setCreatedDate(LocalDateTime.now());
      try {
        productInformationRepository.save(item);
      }catch (Exception e){}
    }
    return null;
  }

  @Override
  public ResponseWrapper update(ProductResponse request) {
    if(request.getId()!=null){
      try{
      ProductResponse productResponse = this.getDetail(request.getId());
        ProductEntity productupdate = new ProductEntity();
        productupdate.setId(request.getId());
        productupdate.setCode(productResponse.getCode());
        productupdate.setName(request.getName());
        productupdate.setDescription(request.getDescription());
        productupdate.setCategoryId(request.getCategoryId());
        productupdate.setProductionId(request.getProductionId());
        productupdate.setSalePrice(request.getSalePrice());
        productupdate.setImportPrice(request.getImportPrice());
        productupdate.setDiscount(request.getDiscount());
        productupdate.setQuantity(request.getQuantity());
        productupdate.setStatus(request.getStatus());
        productupdate.setAvatarUrl(request.getAvatarUrl());
        productupdate.setCreatedDate(productResponse.getCreatedDate());
        productupdate.setModifiedDate(LocalDateTime.now());
        repository.save(productupdate);
        for (ProductInformationEntity itemUpdate: request.getListInformation()) {
          if(itemUpdate.getId()!=null || itemUpdate.getId().equals(0)){
            ProductInformationEntity info = productInformationRepository.findFirstById(itemUpdate.getId());
            itemUpdate.setProductId(info.getProductId());
            itemUpdate.setCreatedDate(info.getCreatedDate());
            itemUpdate.setModifiedDate(LocalDateTime.now());
            productInformationRepository.save(itemUpdate);
          }else{
            itemUpdate.setProductId(request.getId());
            itemUpdate.setCreatedDate(LocalDateTime.now());
            productInformationRepository.save(itemUpdate);
          }
        }
        return new ResponseWrapper(EnumResponse.SUCCESS, request);
      }catch (Exception e){
        return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
      }
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }

  @Override
  public ResponseWrapper deleteById(Long id) {
    var res = this.getDetail(id);
    if(res !=null){
      if(!res.getListInformation().isEmpty()){
        for ( ProductInformationEntity infor : res.getListInformation()) {
          productInformationRepository.deleteById(infor.getId());
        }
      }
      repository.deleteById(res.getId());
      return new ResponseWrapper(EnumResponse.SUCCESS,res);
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }


}
