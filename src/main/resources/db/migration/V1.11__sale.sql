create table s_sale
(
    id            bigserial
        constraint s_sale_pk primary key,
    name          varchar(255),
    key           varchar(255) UNIQUE,
    percentage    NUMERIC(5, 2),
    discount_price         bigint,
    is_percent    boolean not null,
    start_date    timestamp                           not null,
    end_date      timestamp,
    created_date  timestamp default current_timestamp not null,
    modified_date timestamp

)