CREATE SCHEMA IF NOT EXISTS `eshop`;
USE `eshop` ;

create table if not exists eshop.customers(
    name varchar(32) not null,
    address varchar(128) not null,
    email varchar(128) not null,
    
    primary key(name)
);

create table if not exists eshop.order(
	deliveryId varchar(128) not null,
    address varchar(32) not null,
    name varchar(128) not null,
    email varchar(128) not null,
    status varchar(128) not null,
    orderDate date,
    orderId varchar(8) not null,

    primary key (orderId)
);

create table if not exists eshop.line_item(
	item varchar(128) not null,
    quantity int default '1',
    orderId varchar(8) not null,

    primary key (orderId),
    constraint fk_orderId
        foreign key(orderId) references eshop.order(orderId)
);

create table if not exists eshop.order_status(
	order_id varchar(8) not null,
    delivery_id varchar(128) not null,
    status enum ('pending', 'dispatched'),
    status_update timestamp

);


-- if column has been specified, must have matching number of values args
-- key in null as value if required
insert into customers(name, address, email) values('fred','201 Cobblestone Lane','fredflintstone@bedrock.com');
insert into customers(name, address, email) values('sherlock','221B Baker Street, London','sherlock@consultingdetective.org');
insert into customers(name, address, email) values('spongebob','124 Conch Street, Bikini Bottom','spongebob@yahoo.com');
insert into customers(name, address, email) values('jessica','698 Candlewood Land, Cabot Cove','fletcher@gmail.com');
insert into customers(name, address, email) values('dursley','4 Privet Drive, Little Whinging, Surrey','dursley@gmail.com');