alter table p_product
    add column warranty bigint default 0;


alter table bd_bill_detail
    add column warranty_end_date timestamp;