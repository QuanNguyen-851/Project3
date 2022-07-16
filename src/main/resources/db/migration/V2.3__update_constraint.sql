ALTER TABLE pi_product_image
    ADD FOREIGN KEY(image_id)
        REFERENCES i_image (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE pi_product_information
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE p_product
    ADD FOREIGN KEY(category_id)
        REFERENCES c_category (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE p_product
    ADD FOREIGN KEY(production_id)
        REFERENCES p_production (id) ON DELETE CASCADE ON UPDATE CASCADE;
-- ALTER TABLE p_product
--     ADD  FOREIGN KEY(created_by)
--         REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE ps_product_sold
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE b_bill
    ADD FOREIGN KEY(profile_id)
        REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE b_bill
    ADD FOREIGN KEY(voucher_id)
        REFERENCES v_voucher (id) ON DELETE CASCADE ON UPDATE CASCADE;



ALTER TABLE bd_bill_detail
    ADD FOREIGN KEY(bill_id)
        REFERENCES b_bill (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE bd_bill_detail
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE;


ALTER TABLE ip_import_product
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE s_sale
    ADD FOREIGN KEY(product_id)
        REFERENCES p_product (id) ON DELETE CASCADE ON UPDATE CASCADE;
alter TABLE s_sale
    ADD FOREIGN KEY(created_by)
        REFERENCES p_profile (id) ON DELETE CASCADE ON UPDATE CASCADE;