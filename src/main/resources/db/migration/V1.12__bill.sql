create table b_bill
(
    id            bigserial
        constraint b_bill_pk primary key,
    profile_id    bigint not null,
    sale_id       bigint,
    description   text,
    total_price   bigint,
    owner_name    varchar(255) not null,
    phone         varchar(11) not null,
    email         varchar(255) not null,
    address       varchar(255) not null,
    status        varchar(255) ,
    type          varchar(255),
    created_date  timestamp default current_timestamp not null,
    modified_date timestamp
);

create table bd_bill_detail
(
    id bigserial
        constraint bd_bill_detail_pk primary key,
    bill_id bigint not null,
    product_id bigint not null,
    quantity bigint,
    price bigint,
    UNIQUE (bill_id, product_id)
)