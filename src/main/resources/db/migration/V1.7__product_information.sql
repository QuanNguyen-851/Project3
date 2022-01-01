create table pi_product_information
(
    id bigserial
        constraint pi_product_information_pk primary key,
    product_code varchar (255),
    product_id bigint,
    key varchar (255),
    value varchar (255),
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)