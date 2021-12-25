create table p_city
(
    id          bigserial
        constraint p_city_pk primary key,
    name        varchar(255),
    province_id bigint not null,
    path        varchar(255)
);