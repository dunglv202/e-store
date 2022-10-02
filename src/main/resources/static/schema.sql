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
    `date_created` timestamp not null,
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

create table `recipients` (
    `id` int not null primary key auto_increment,
    `name` varchar(80) not null,
    `phone_number` varchar(20) not null,
    `address` varchar(150) not null
);

create table `orders` (
    `id` int not null primary key auto_increment,
    `user_id` int not null,
    `recipient_id` int not null,
    `payment_method` varchar(80) not null,
    `notes` text,
    `status` varchar(80) not null,
    `date_created` timestamp not null,

    constraint `fk_orders_users` foreign key (`user_id`) references `users` (`id`),
    constraint `fk_orders_recipients` foreign key (`recipient_id`) references `recipients` (`id`)
);

create table `order_items` (
    `id` int not null primary key auto_increment,
    `product_id` int not null,
    `quantity` int not null,
    `order_id` int not null,

    constraint `fk_order_items_order` foreign key (`product_id`) references `products` (`id`)
);

create table `reviews` (
    `id` int not null primary key auto_increment,
    `rating` int not null,
    `comment` text,
    `date_created` timestamp not null,
    `order_item_id` int not null,

    constraint `fk_reviews_order_items` foreign key (`order_item_id`) references `order_items` (`id`)
);

create view `product_ratings` as
select p.`id` as `product_id`, p.`name` as `product_name`, avg(r.rating) as avg_rating
from `products` p
left join `order_items` oi
    on p.`id` = oi.`product_id`
left join `reviews` r
    on oi.`id` = r.`order_item_id`
group by p.`id`;

create table `product_images` (
    `id` int not null primary key auto_increment,
    `image_path` varchar(200) not null,
    `product_id` int not null

#     ,constraint `fk_product_images_products` foreign key (`product_id`) references `products` (`id`)
);
