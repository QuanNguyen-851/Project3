alter table c_category
    add column sort_name varchar(50) UNIQUE;


alter table p_product
    ADD CONSTRAINT product_code_unique UNIQUE (code);

alter table p_product
drop column sub_name;

alter table pi_product_information
drop column product_code;

