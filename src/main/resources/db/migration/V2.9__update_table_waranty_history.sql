ALTER TABLE p_warranty_history
DROP CONSTRAINT if exists p_warranty_history_user_id_fkey;

ALTER TABLE p_warranty_history
DROP COLUMN if exists user_id;

ALTER TABLE p_warranty_history
ADD COLUMN if not  exists user_phone varchar(20);

ALTER TABLE p_warranty_history
    ADD COLUMN if not  exists user_name varchar(255);
ALTER TABLE p_warranty_history
    ADD COLUMN if not  exists user_address text;


