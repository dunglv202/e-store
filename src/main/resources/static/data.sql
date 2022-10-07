insert into `authorities` (`name`)
values  ('ROLE_ADMIN'),
        ('ROLE_MANAGER'),
        ('ROLE_STOCK_MANAGER'),
        ('ROLE_STAFF_MANAGER'),
        ('ROLE_SALES_EMPLOYEE'),
        ('ROLE_CUSTOMER');

insert into `role_managements` (`role_id`, `managed_by`)
values  (3, 2),
        (4, 2),
        (5, 4);

insert into `user_details` (`first_name`, `last_name`, `email`)
values  ('Luu Van', 'Dung', 'dunglv202@gmail.com'),
        ('Luu Van', 'Dung', 'vandungsunbin@gmail.com');

insert into `users` (`username`, `password`, `enabled`, `user_details_id`)
values  ('admin', '$2a$12$Tg6Ho44guM0STFCC2rdH5ea7wkPvB0O5l0BLQQqQ7.qCYT2p1EpiS', true, 1),
        ('manager', '$2a$12$Tg6Ho44guM0STFCC2rdH5ea7wkPvB0O5l0BLQQqQ7.qCYT2p1EpiS', true, 2);

insert into `user_authorities` (`user_id`, `authority_id`)
values  (1, 1),
        (2, 2);

insert into `brands` (`name`)
values  ('XIAOMI'),
        ('VIVO'),
        ('OPPO'),
        ('REALME'),
        ('VSMART'),
        ('SAMSUNG'),
        ('HUAWEI'),
        ('APPLE'),
        ('DELL'),
        ('HP'),
        ('ACER'),
        ('ASUS'),
        ('LENOVO');

insert into `categories` (`name`)
values  ('SMARTPHONE'),
        ('LAPTOP');

