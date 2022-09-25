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