create table if not exists bd_bill_detail_imei
(
    id            bigserial
    constraint bd_bill_detail_imei_pk
    primary key,
    bill_detail_id         bigint,
    imei                   text DEFAULT null,
    created_date  timestamp default CURRENT_TIMESTAMP not null,
    modified_date timestamp,
    created_by    bigint,
    modified_by   bigint,
    FOREIGN KEY (bill_detail_id) REFERENCES bd_bill_detail (id) ON DELETE CASCADE ON UPDATE CASCADE
);