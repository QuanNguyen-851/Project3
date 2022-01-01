create table pi_product_image
(
    id bigserial
        constraint pi_product_image_pk primary key,
    owner_id bigint,
    image_id bigint,
    UNIQUE(owner_id, image_id)
)