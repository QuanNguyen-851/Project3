create table ip_import_product
(
    id bigserial
        constraint ip_import_product_pk primary key,
    product_id bigint,
    import_total bigint,
--     quantity bigint default 0,
    created_date  timestamp    default current_timestamp not null,
    created_by bigint
)