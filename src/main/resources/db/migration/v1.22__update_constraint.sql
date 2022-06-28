ALTER TABLE pi_product_image
    ADD FOREIGN KEY(image_id)
        REFERENCES i_image (id);

ALTER TABLE pi_product_information
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id);

ALTER TABLE p_product
    ADD FOREIGN KEY(category_id)
        REFERENCES c_category (id);
ALTER TABLE p_product
    ADD FOREIGN KEY(production_id)
        REFERENCES p_production (id);
ALTER TABLE p_product
    ADD  FOREIGN KEY(created_by)
        REFERENCES p_profile (id);


ALTER TABLE ps_product_sold
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id);


ALTER TABLE b_bill
    ADD FOREIGN KEY(profile_id)
        REFERENCES p_profile (id);
ALTER TABLE b_bill
    ADD FOREIGN KEY(voucher_id)
        REFERENCES v_voucher (id);



ALTER TABLE bd_bill_detail
    ADD FOREIGN KEY(bill_id)
        REFERENCES b_bill (id);
ALTER TABLE bd_bill_detail
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id);


ALTER TABLE ip_import_product
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id);

ALTER TABLE s_sale
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id);
alter TABLE s_sale
    ADD FOREIGN KEY(created_by)
        REFERENCES p_profile (id);