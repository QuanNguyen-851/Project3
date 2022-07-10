DROP table if exists sc_shopping_cart_detail;

DROP table if exists sc_shopping_cart;

create table if not exists sc_shopping_cart
(
    id            bigserial
    constraint sc_shopping_cart_pk
    primary key,
    profile_id    bigint ,
    product_id         bigint,
    quantity           bigint,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (product_id) REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (profile_id) REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE
);
