create table if not exists n_notification
(
    id            bigserial
    constraint n_notification_pk
    primary key,
    sender_id  bigint ,
    profile_id    bigint ,
    title         varchar (255),
    body          text,
    is_read       BOOLEAN default FALSE ,
    params        text ,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (sender_id) REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (profile_id) REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE
);