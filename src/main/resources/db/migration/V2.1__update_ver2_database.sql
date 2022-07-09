create table if not exists sc_shopping_cart
(
    id            bigserial
        constraint sc_shopping_cart_pk
            primary key,
    profile_id    bigint unique ,
    total_price      bigint,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (profile_id) REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE
);

create table  if not exists sc_shopping_cart_detail
(
    id            bigserial
        constraint sc_shopping_cart_detail_pk
            primary key,
    shopping_cart_id    bigint,
    product_id         bigint,
    quantity           bigint,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (shopping_cart_id) REFERENCES sc_shopping_cart (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE
);