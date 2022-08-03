create table if not exists p_warranty_history
(
    id            bigserial
    constraint p_warranty_history_pk
    primary key,
    imei                   text not null ,
    product_id             bigint,
    user_id                bigint,
    product_condition       text not null,
    status                  varchar (255),
    surcharge               bigint default 0,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (product_id) REFERENCES p_product(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES p_profile(id) ON DELETE CASCADE ON UPDATE CASCADE
);
