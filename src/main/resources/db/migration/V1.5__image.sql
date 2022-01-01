create table i_image
(
    id bigserial
        constraint i_image_pk primary key,
    image_url text,
    title varchar (255),
    type varchar (255),
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)