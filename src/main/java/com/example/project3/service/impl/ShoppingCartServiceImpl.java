package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.entity.ShoppingCartDetailEntity;
import com.example.project3.model.entity.ShoppingCartEntity;
import com.example.project3.repository.ShoppingCartDetailRepository;
import com.example.project3.repository.ShoppingCartRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ShoppingCartService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private ShoppingCartDetailRepository shoppingCartDetailRepository;

  @Autowired
  private Token token;

  @Override
  public ResponseWrapper createOrUpdate(Long productId, Long quantity) {
    Long profileId = Long.parseLong(token.sub("id"));
    if (profileId==null){
      return new ResponseWrapper(EnumResponse.ACCESSDENIED,null);
    }
    var shoppingCartEntity = shoppingCartRepository.findFirstByProfileId(profileId);
    if(shoppingCartEntity==null){
      var cartRes=createShoppingCart(profileId);
      addProductToShoppingCart(cartRes.getId(), productId, quantity);
      return new ResponseWrapper(EnumResponse.SUCCESS,cartRes);
    }else{
      updateProductQuantityToShoppingCart(shoppingCartEntity.getId(), productId, quantity);
      return new ResponseWrapper(EnumResponse.SUCCESS,shoppingCartEntity);
    }

  }

  private ShoppingCartEntity createShoppingCart(Long profileId){

    return shoppingCartRepository.save(
        ShoppingCartEntity.builder()
            .profileId(profileId)
            .totalPrice(0L)
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .createdBy(profileId)
            .modifiedBy(profileId)
            .build()
    );
  }

  private ShoppingCartDetailEntity addProductToShoppingCart(Long shoppingCartId, Long productId, Long quantity){
    return shoppingCartDetailRepository.save(
      ShoppingCartDetailEntity.builder()
          .shoppingCartId(shoppingCartId)
          .productId(productId)
          .quantity(quantity)
          .createdDate(LocalDateTime.now())
          .modifiedDate(LocalDateTime.now())
          .build()
    );
  }
  private ShoppingCartDetailEntity updateProductQuantityToShoppingCart(Long shoppingCartId, Long productId, Long quantity){
    ShoppingCartDetailEntity shoppingCartDetailEntity =  shoppingCartDetailRepository.findFirstByShoppingCartIdAndProductId(shoppingCartId, productId);
    if(shoppingCartDetailEntity.getId()==null){
      addProductToShoppingCart(shoppingCartId, productId, quantity);
    }
    Long num = shoppingCartDetailEntity.getQuantity() + quantity;
    return shoppingCartDetailRepository.save(
        ShoppingCartDetailEntity.builder()
            .id(shoppingCartDetailEntity.getId())
            .shoppingCartId(shoppingCartDetailEntity.getShoppingCartId())
            .productId(productId)
            .quantity(num)
            .createdDate(shoppingCartDetailEntity.getCreatedDate())
            .modifiedDate(LocalDateTime.now())
            .build()
    );
  }
}
