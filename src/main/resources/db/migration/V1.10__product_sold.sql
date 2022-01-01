create table ps_product_sold
(
    id bigserial
        constraint ps_product_sold_pk primary key,
    product_id bigint,
    product_code varchar(255),
    product_name varchar (255),
    date timestamp not null,
    sold bigint default 0,
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)