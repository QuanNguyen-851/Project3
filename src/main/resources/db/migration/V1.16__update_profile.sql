ALTER TABLE p_profile
DROP column  date_birth ;
ALTER TABLE p_profile
    ADD COLUMN date_birth date;
ALTER TABLE p_profile
DROP column  block ;
ALTER TABLE  p_profile
    ADD column block boolean DEFAULT false;
ALTER TABLE p_profile
DROP column  image_id ;
ALTER TABLE  p_profile
    ADD column image_url text ;

INSERT INTO p_profile (fist_name, last_name, phone, email, gender, pass_word,date_birth, address, role, block )
values ('Nguyễn ',' Quân', '0986680849','quan@gmail.com', 'MALE', '123123', '2001/05/08', 'HM-HN','SUPERADMIN', false),
       ('Nguyễn ',' Nhị', '0912345678','nhi@gmail.com', 'MALE', '123123', '2001/01/01', 'HM-HN','SUPERADMIN', false)