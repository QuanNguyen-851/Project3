create table p_production
(
    id bigserial
        constraint p_production_pk primary key,
    name varchar (255),
    company varchar (255),
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)