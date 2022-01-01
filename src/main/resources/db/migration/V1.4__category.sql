create table c_category
(
    id bigserial
        constraint c_category_pk primary key,
    name varchar (255),
    title varchar(255),
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp
)