package com.example.project3.service.impl;

import com.example.project3.Common.Token;
import com.example.project3.model.dto.ShoppingCartRequest;
import com.example.project3.model.dto.ShoppingCartResponse;
import com.example.project3.model.dto.ShoppingCartResponsePage;
import com.example.project3.model.entity.ProductEntity.ProductEnum;
import com.example.project3.model.entity.SaleEntity;
import com.example.project3.model.entity.ShoppingCartDetailEntity;
import com.example.project3.model.entity.ShoppingCartEntity;
import com.example.project3.repository.ProductRepository;
import com.example.project3.repository.ProfileRepository;
import com.example.project3.repository.ShoppingCartDetailRepository;
import com.example.project3.repository.ShoppingCartRepository;
import com.example.project3.response.EnumResponse;
import com.example.project3.response.ResponseWrapper;
import com.example.project3.service.ProductService;
import com.example.project3.service.ShoppingCartService;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private Token token;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProfileRepository profileRepository;

  @Override
  public ResponseWrapper createOrUpdate(Long productId, Long quantity) {
    Long profileId = Long.parseLong(token.sub("id"));
    if (profileId == null) {
      return new ResponseWrapper(EnumResponse.ACCESSDENIED, null);
    }
    var prod = productRepository.findFirstById(productId);
    if(prod.getStatus().equals(ProductEnum.PAUSE.name())){
      return new ResponseWrapper(EnumResponse.FAIL, productId, "sản phẩm này hiện đã ngừng kinh doanh!");
    }else{

    }

    var item = shoppingCartRepository.findFirstByProfileIdAndProductId(profileId, productId);
    if (item == null) {
      if(quantity>prod.getQuantity()){
        return new ResponseWrapper(EnumResponse.FAIL, productId,
            String.format("Sản phẩm này trong kho chỉ còn %d ", prod.getQuantity()));
      }
      var res = shoppingCartRepository.save(ShoppingCartEntity.builder()
          .profileId(profileId)
          .productId(productId)
          .quantity(quantity)
          .createdDate(LocalDateTime.now())
          .modifiedDate(LocalDateTime.now())
          .createdBy(profileId)
          .modifiedBy(profileId)
          .build());
      return new ResponseWrapper(EnumResponse.SUCCESS, res);
    }
    Long num = item.getQuantity() + quantity;
    if (num <= 0) {
      shoppingCartRepository.deleteById(item.getId());
      return new ResponseWrapper(EnumResponse.SUCCESS, null);
    }
    if(num>prod.getQuantity()){
      return new ResponseWrapper(EnumResponse.FAIL, productId,
          String.format("Sản phẩm này trong kho chỉ còn %d ", prod.getQuantity()));
    }
    var res = shoppingCartRepository.save(ShoppingCartEntity.builder()
        .id(item.getId())
        .profileId(item.getProfileId())
        .productId(productId)
        .quantity(num)
        .createdDate(item.getCreatedDate())
        .modifiedDate(LocalDateTime.now())
        .createdBy(item.getCreatedBy())
        .modifiedBy(profileId)
        .build());
    return new ResponseWrapper(EnumResponse.SUCCESS, res);

  }

  @Override
  public ShoppingCartResponsePage getDetailShoppingCart() {
    Long myId = Long.parseLong(token.sub("id"));
    var myCart = shoppingCartRepository.findAllByProfileIdOrderByIdDesc(myId);
    if (CollectionUtils.isEmpty(myCart)) {
      return ShoppingCartResponsePage.builder().total(0L).build();
    }
    var list = myCart.stream()
        .map(cart->{
          var prod = productService.getDetail(cart.getProductId());
          return ShoppingCartResponse.builder()
            .createDate(cart.getCreatedDate())
            .quantity(cart.getQuantity())
            .price(salePrice(prod.getSalePrice(), cart.getQuantity(), prod.getSaleEntity()))
            .product(prod)
            .build();
        }).collect(Collectors.toList());
    return ShoppingCartResponsePage.builder()
        .products(list)
        .total((long) list.size())
        .build();
  }
  public Long salePrice(Long price, Long quantity, SaleEntity saleEntity){
    Long totalPrice = price * quantity;
    if(saleEntity!=null
        && saleEntity.getStartDate().isBefore(LocalDateTime.now())
        && saleEntity.getEndDate().isAfter(LocalDateTime.now())){
        Float floatPrice =( totalPrice - (totalPrice * saleEntity.getSale() /100 ));
        totalPrice = (floatPrice.longValue()<=0L)?0L:floatPrice.longValue();
    }
    return totalPrice;

  }

  @Override
  public ResponseWrapper removeProduct(Long productId) {
    var entity  = shoppingCartRepository.findFirstByProfileIdAndProductId( Long.parseLong(token.sub("id")), productId);
    if(entity==null){
      return new ResponseWrapper(EnumResponse.NOT_FOUND, null);
    }
    shoppingCartRepository.deleteById(entity.getId());
     return new ResponseWrapper(EnumResponse.SUCCESS, null);
  }

  @Override
  public ResponseWrapper updateProduct(ShoppingCartRequest request) {
    if(request.getQuantity()<=0){
      return removeProduct(request.getProductId());
    }
    var entity  = shoppingCartRepository.findFirstByProfileIdAndProductId( Long.parseLong(token.sub("id")), request.getProductId());
    if(entity==null){
      return createOrUpdate(request.getProductId(), request.getQuantity());
    }
    entity.setQuantity(request.getQuantity());
    entity.setModifiedDate(LocalDateTime.now());
    entity.setProfileId(Long.parseLong(token.sub("id")));
    return new ResponseWrapper(EnumResponse.SUCCESS, shoppingCartRepository.save(entity));
  }


}
