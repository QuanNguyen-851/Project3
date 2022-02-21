create table s_sale(
    id bigserial
        constraint s_saleprod_pk primary key,
    product_id bigint,
    sale NUMERIC(5, 2),
    start_date    timestamp  not null,
    end_date      timestamp,
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp,
    created_by bigint
)