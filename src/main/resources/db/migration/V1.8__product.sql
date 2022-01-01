create table p_product
(
    id bigserial
        constraint p_product_pk primary key,
    code varchar (255),
    name varchar(255),
    sub_name varchar(255),
    description text,
    category_id bigint,
    production_id bigint,
    price bigint,
    quantity bigint,
    status varchar (255),
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)