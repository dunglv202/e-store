create table `categories` (
    `id` int not null primary key auto_increment,
    `name` varchar(100) not null
);

create table `brands` (
    `id` int not null primary key auto_increment,
    `name` varchar(100) not null
);

create table `products` (
    `id` int not null primary key auto_increment,
    `name` varchar(200) not null,
    `description` text,
    `preview` varchar(200),
    `price` double not null ,
    `in_stock_quantity` int not null,
    `category_id` int,
    `brand_id` int,

    constraint `fk_category` foreign key (`category_id`) references `categories`(`id`),
    constraint `fk_brand` foreign key (`brand_id`) references `brands`(`id`)
);

create table `user_details` (
    `id` int not null primary key auto_increment,
    `first_name` varchar(80),
    `last_name` varchar(80) not null,
    `email` varchar(200) not null
);

create table `users` (
    `id` int not null primary key auto_increment,
    `username` varchar(64) not null unique,
    `password` varchar(256) not null,
    `enabled` boolean not null,
    `user_details_id` int not null
);

create table `authorities` (
    `id` int not null primary key auto_increment,
    `name` varchar(80) not null unique
);

create table `user_authorities` (
    `user_id` int not null,
    `authority_id` int not null,

    constraint `uni_user_authorities` unique (`user_id`, `authority_id`),
    constraint `fk_user` foreign key (`user_id`) references `users`(`id`),
    constraint `fk_authority` foreign key (`authority_id`) references `authorities`(`id`)
);

create table `cart_items` (
    `id` int not null primary key auto_increment,
    `product_id` int not null,
    `quantity` int not null,
    `user_id` int not null,

    constraint `fk_product` foreign key (`product_id`) references `products`(`id`),
    constraint `fk_cart_user` foreign key (`user_id`) references `users`(`id`)
);