package com.example.project3.Common;

import com.example.project3.model.dto.BillDTO;
import com.example.project3.model.dto.BillDetailResponse;
import com.example.project3.model.dto.ProductDTO;
import com.example.project3.model.dto.WarrantyHistoryResponse;
import com.example.project3.model.entity.BillDetailEntity;
import com.example.project3.model.entity.BillEntity;
import com.example.project3.model.entity.ProductEntity;
import com.example.project3.model.entity.ProductResponse;
import com.example.project3.model.entity.ProfileEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity;
import com.example.project3.model.entity.WarrantyHistoryEntity.WarrantyHistoryStatus;

public class Maper {

  private static final Maper INSTANCE = new Maper();

  // Private constructor to avoid client applications to use constructor
  private Maper() {

  }

  public static Maper getInstance() {
    return INSTANCE;
  }

  public ProductDTO ProductEntityToDTO(ProductEntity productEntity) {
    return ProductDTO.builder()
        .id(productEntity.getId())
        .code(productEntity.getCode())
        .name(productEntity.getName())
        .description(productEntity.getDescription())
        .categoryId(productEntity.getCategoryId())
        .productionId(productEntity.getProductionId())
        .salePrice(productEntity.getSalePrice())
        .importPrice(productEntity.getImportPrice())
        .discount(productEntity.getDiscount())
        .quantity(productEntity.getQuantity())
        .status(productEntity.getStatus())
        .build();
  }

  public BillDTO BillEntityToBillDTO(BillEntity billEntity) {
    return BillDTO.builder()
        .id(billEntity.getId())
        .profileId(billEntity.getProfileId())
        .voucherId(billEntity.getVoucherId())
        .description(billEntity.getDescription())
        .totalPrice(billEntity.getTotalPrice())
        .discountPrice(billEntity.getDiscountPrice())
        .ownerName(billEntity.getOwner_name())
        .phone(billEntity.getPhone())
        .email(billEntity.getEmail())
        .address(billEntity.getAddress())
        .status(billEntity.getStatus())
        .type(billEntity.getType())
        .createdDate(billEntity.getCreatedDate())
        .modifiedDate(billEntity.getModifiedDate())
        .build();
  }
  public BillEntity BillDTOToBillEntity(BillDTO billDTO){
    BillEntity billEntity = new BillEntity();
    billEntity.setProfileId(billDTO.getProfileId());
    billEntity.setVoucherId(billDTO.getVoucherId());
    billEntity.setDescription(billDTO.getDescription());
    billEntity.setTotalPrice(billDTO.getTotalPrice());
    billEntity.setDiscountPrice(billDTO.getDiscountPrice());
    billEntity.setOwner_name(billDTO.getOwnerName());
    billEntity.setPhone(billDTO.getPhone());
    billEntity.setEmail(billDTO.getEmail());
    billEntity.setAddress(billDTO.getAddress());
    billEntity.setStatus(billDTO.getStatus());
    billEntity.setType(billDTO.getType());
    billEntity.setCreatedDate(billDTO.getCreatedDate());
    billEntity.setModifiedDate(billDTO.getModifiedDate());
    return billEntity;
  }
  public BillDetailEntity ToBillDetailEntity(BillDetailResponse billDetailResponse){
    BillDetailEntity billDetailEntity = new BillDetailEntity();
    billDetailEntity.setBillId(billDetailResponse.getBillId());
    billDetailEntity.setProductId(billDetailResponse.getProductId());
    billDetailEntity.setQuantity(billDetailResponse.getQuantity());
    billDetailEntity.setWarrantyEndDate(billDetailResponse.getWarrantyEndDate());
    billDetailEntity.setPrice(billDetailResponse.getPrice());
    return billDetailEntity;
  }

  public BillDetailResponse DetailEntityToResponse(BillDetailEntity billDetailEntity) {
    return BillDetailResponse.builder()
        .id(billDetailEntity.getId())
        .billId(billDetailEntity.getBillId())
        .productId(billDetailEntity.getProductId())
        .quantity(billDetailEntity.getQuantity())
        .price(billDetailEntity.getPrice())
        .warrantyEndDate(billDetailEntity.getWarrantyEndDate())
        .build();
  }

  public WarrantyHistoryResponse toWarrantyHistoryResponse(WarrantyHistoryEntity entity){
    ProductResponse prod = new ProductResponse();
    prod.setId(entity.getProductId());
    ProfileEntity profile = new ProfileEntity();
    profile.setId(entity.getUserId());
    return WarrantyHistoryResponse.builder()
        .id(entity.getId())
        .imei(entity.getImei())
        .product(prod)
        .profile(profile)
        .status(WarrantyHistoryStatus.valueOf(entity.getStatus()))
        .surcharge(entity.getSurcharge())
        .createdDate(entity.getCreatedDate())
        .productCondition(entity.getProductCondition())
        .build();
  }
}
