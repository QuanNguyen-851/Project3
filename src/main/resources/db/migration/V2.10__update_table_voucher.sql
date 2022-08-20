ALTER TABLE v_voucher
    ADD COLUMN if not  exists min_price bigint default 0;
ALTER TABLE v_voucher
    ADD COLUMN if not  exists quantity bigint default 0;