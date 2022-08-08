alter table b_bill
    add column reason text default null;
alter table b_bill
    add column modified_by bigint;
