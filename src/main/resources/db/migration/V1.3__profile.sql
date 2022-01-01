create table p_profile(
    id bigserial
    constraint p_profile_pk primary key,
    fist_name  varchar(255),
    last_name varchar (255),
    image_id bigint ,
    phone varchar(11) UNIQUE ,
    email varchar (255) UNIQUE ,
    gender varchar(10),
    pass_word varchar (255),
    date_birth timestamp ,
    address varchar (255),
    role varchar (255),
    block varchar (10) default false,
    created_date  timestamp    default current_timestamp not null,
    modified_date timestamp

)
