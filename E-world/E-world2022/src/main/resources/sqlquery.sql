CREATE DATABASE e_world;
USE e_world;
CREATE TABLE product(
created_by VARCHAR(100) NOT NULL,
created_at DATE NOT NULL,
updated_by VARCHAR(100) NOT NULL,
updated_at DATE NOT NULL,
id INT IDENTITY(1,1) primary key,
category_id int  NOT NULL,
name VARCHAR(255) NOT NULL,
price FLOAT NOT NULL,
quantity int not null,
images VARCHAR(255) NOT NULL,
urlvideo VARCHAR(255) NOT NULL,
ngaybaohanh DATE NOT NULL,
models INT NOT NULL,
description VARCHAR(255) NOT NULL,
status VARCHAR(255) NOT NULL,
UNIQUE(models),
FOREIGN KEY(category_id) REFERENCES category(id)
);

CREATE TABLE orders(
created_at DATE NOT NULL,
id INT IDENTITY(1,1) PRIMARY KEY,
user_id INT  NOT NULL,
phone VARCHAR(15) NOT NULL,
address VARCHAR(255) NOT NULL,
total_price FLOAT NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE order_detail(
id INT IDENTITY(1,1) PRIMARY KEY,
order_id INT NOT NULL,
product_id INT  NOT NULL,
quantity INT NOT NULL,
product_price FLOAT NOT NULL,
status VARCHAR(255) NOT NULL,
FOREIGN KEY(order_id) REFERENCES orders(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);
go
CREATE TABLE category(
id INT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255) NOT NULL,
logo VARCHAR(255) NOT NULL
);

CREATE TABLE brand(
id INT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255) NOT NULL,
logo VARCHAR(255) NOT NULL
);

CREATE TABLE category_brand(
id  INT IDENTITY(1,1) PRIMARY KEY,
category_id INT NOT NULL,
brand_id INT NOT NULL,
FOREIGN KEY (category_id) REFERENCES category(id),
FOREIGN KEY (brand_id) REFERENCES brand(id)
);

CREATE TABLE users(
id INT IDENTITY(1,1) PRIMARY KEY,
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
authorities_id INT NOT NULL,
UNIQUE(password),
FOREIGN KEY (authorities_id) REFERENCES authorities(id)
);

CREATE TABLE authorities(
id INT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255) NOT NULL
);
CREATE TABLE personal_information(
id INT IDENTITY(1,1) PRIMARY KEY,
user_id  INT NOT NULL,
authorities_id INT NOT NULL,
firstname VARCHAR(255) NOT NULL,
lastname VARCHAR(255) NOT NULL,
age int NOT NULL,
address VARCHAR(255) NOT NULL,
nationality VARCHAR(255) NOT NULL,
date_of_birth DATE NOT NULL,
email VARCHAR(255) NOT NULL,
phone VARCHAR(15) NOT NULL,
FOREIGN KEY(user_id) REFERENCES users(id),
FOREIGN KEY(authorities_id) REFERENCES authorities(id)
);
INSERT INTO authorities (name) values ('Staff'),('Admin'),('User');
SELECT  * FROM authorities;
INSERT INTO users(username,password,authorities_id,created_at) values
('sa','123','2',now()),
('sa2','2002','2',now());
SELECT * FROM users;
