package com.example.project3.service.impl;

import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.entity.ImageEntity;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductEntity.ProductEnum;
import com.example.project3.model.entity.ProductImageEntity;
import com.example.project3.model.entity.ProductInformationEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.enumpk.ImageType;
import com.example.project3.repository.CategoryRepository;
import com.example.project3.repository.ImageRepository;
import com.example.project3.repository.ProductImageRepository;
import com.example.project3.repository.ProductInformationRepository;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProductionRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
  private ProfileRepository profileRepository;
  @Autowired
  private ImageRepository imageRepository;

  @Override
  public List<ProductResponse> getAll(
      String status,
      String code,
      String name,
      Long idCate,
      Long idProduction
  ) {
    return repository.getAllProduct(
        status,
        code,
        name,
        idCate,
        idProduction
    );
  }

  @Override
  public ProductResponse getDetail(Long id) {
    try {
      var product = repository.getProductById(id);
      var createprofile = profileRepository.getById(product.getCreatedBy());
      var modifiedprofile = profileRepository.getById(product.getModifiedBy());
      product.setListInformation(productInformationRepository.findAllByProductId(id));
      product.setListImage(imageRepository.getProductImageByIdProduct(id));
      product.setCreatedByName(createprofile.getFistName() +createprofile.getLastName());
      product.setModifiedByName(modifiedprofile.getFistName() +modifiedprofile.getLastName());

      return product;
    } catch (Exception e) {
      return new ProductResponse();
    }

  }

  @Override
  public ResponseWrapper create(ProductDTO productDTO) {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(productDTO.getName());
    productEntity.setDescription(productDTO.getDescription());
    productEntity.setCategoryId(productDTO.getCategoryId());
    productEntity.setProductionId(productDTO.getProductionId());
    productEntity.setSalePrice(productDTO.getSalePrice());
    productEntity.setImportPrice(productDTO.getImportPrice());
    productEntity.setDiscount(productDTO.getDiscount());
    productEntity.setQuantity(productDTO.getQuantity());
    productEntity.setStatus(ProductEnum.ACTIVE.name());
    productEntity.setAvatarUrl(productDTO.getAvatarUrl());
    productEntity.setCreatedDate(LocalDateTime.now());
    productEntity.setModifiedDate(LocalDateTime.now());
    productEntity.setCreatedBy(productDTO.getCreatedBy());
    productEntity.setModifiedBy(productDTO.getCreatedBy());
//    repository.save(productEntity);
    String sortName = categoryRepository.getById(productDTO.getCategoryId()).getSortName();
//    ProductEntity newProduct = repository.getNewProduct();
    ProductEntity newProduct = repository.save(productEntity);
    newProduct.setCode(sortName + newProduct.getId());
    repository.save(newProduct);
    if (productDTO.getListInformation() != null) {
      for (ProductInformationEntity item : productDTO.getListInformation()) {
        this.setProductInfor(item, newProduct.getId());
      }
    }
    if (productDTO.getListImage() != null) {
      for (ImageEntity item : productDTO.getListImage()) {
        this.setImage(item, newProduct.getId());
      }
    }
    return new ResponseWrapper(EnumResponse.SUCCESS, productDTO);
  }


  private Void setImage(ImageEntity item, Long productId) {
    item.setType(ImageType.PRODUCT.name());
    item.setCreatedDate(LocalDateTime.now());
    item.setModifiedDate(LocalDateTime.now());
    var newimage = imageRepository.save(item);
    ProductImageEntity pImage = new ProductImageEntity();
    pImage.setImageId(newimage.getId());
    pImage.setOwnerId(productId);
    productImageRepository.save(pImage);
    return null;
  }

  private Void setProductInfor(ProductInformationEntity item, Long productId) {
    item.setProductId(productId);
    item.setCreatedDate(LocalDateTime.now());
    item.setModifiedDate(LocalDateTime.now());
    try {
      productInformationRepository.save(item);
    } catch (Exception e) {
    }
    return null;
  }

  @Override
  public ResponseWrapper update(ProductDTO request) {
    if (request.getId() != null) {
      try {
        ProductEntity productResponse = repository.getById(request.getId());
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
        productupdate.setCreatedBy(productResponse.getCreatedBy());
        productupdate.setModifiedBy(request.getModifiedBy());
        repository.save(productupdate);
        for (ProductInformationEntity itemUpdate : request.getListInformation()) {
          this.updateProductInfor(itemUpdate, request.getId());
        }
        for (ImageEntity imageUpdate : request.getListImage()) {
          this.updateImage(imageUpdate, request.getId(), imageUpdate.getId());
        }
        return new ResponseWrapper(EnumResponse.SUCCESS, request);
      } catch (Exception e) {
        return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
      }
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }

  private Void updateImage(ImageEntity item, Long productId, Long imageId) {
    if (item.getId() != null) {
      ImageEntity image = imageRepository.findFirstById(imageId);
      item.setCreatedDate(image.getCreatedDate());
      item.setModifiedDate(LocalDateTime.now());
    } else {
      this.setImage(item, productId);
    }
    return null;
  }

  private Void updateProductInfor(ProductInformationEntity itemUpdate, Long productId) {
    if (itemUpdate.getId() != null) {
      ProductInformationEntity info = productInformationRepository.findFirstById(itemUpdate.getId());
      itemUpdate.setProductId(info.getProductId());
      itemUpdate.setCreatedDate(info.getCreatedDate());
      itemUpdate.setModifiedDate(LocalDateTime.now());
      productInformationRepository.save(itemUpdate);
    } else {
      this.setProductInfor(itemUpdate, productId);
    }
    return null;
  }


  @Override
  public ResponseWrapper deleteById(Long id) {
    var res = this.getDetail(id);
    if (res != null) {
      if (!res.getListInformation().isEmpty()) {
        for (ProductInformationEntity infor : res.getListInformation()) {
          productInformationRepository.deleteById(infor.getId());
        }
      }
      repository.deleteById(res.getId());
      return new ResponseWrapper(EnumResponse.SUCCESS, res);
    }
    return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
  }


}
