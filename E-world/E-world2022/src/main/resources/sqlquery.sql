CREATE DATABASE e_world;
go
USE e_world;
go
CREATE TABLE role(
id VARCHAR(50) PRIMARY KEY,
name VARCHAR(100) NOT NULL
)
go
CREATE TABLE category(
id INT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255) NOT NULL,
logo VARCHAR(255) NOT NULL,
status VARCHAR(100) NOT NULL
);
go
CREATE TABLE product(
created_at DATE NOT NULL,
id INT IDENTITY(1,1) primary key,
category_id int  NOT NULL,
name VARCHAR(255) NOT NULL,
price FLOAT NOT NULL,
quantity int  NOT NULL,
urlvideo VARCHAR(255),
ngaybaohanh DATE NOT NULL,
models INT NOT NULL,
description VARCHAR(255),
status VARCHAR(255) NOT NULL,
image VARCHAR(255) NOT NULL
UNIQUE(models),
FOREIGN KEY(category_id) REFERENCES category(id)
);
go
CREATE TABLE account(
create_at DATE NOT NULL,
id INT IDENTITY(1,1) PRIMARY KEY,
username VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
phone VARCHAR(20) ,
first_name VARCHAR(100),
last_name VARCHAR(100),
gioitinh VARCHAR(10),
age INT ,
date_of_birth DATE,
address NVARCHAR(255),
nationality VARCHAR(100),
image VARCHAR(100),
status VARCHAR(100) NOT NULL,
enabled BIT NOT NULL
);
go
CREATE TABLE orders(
created_at DATE NOT NULL,
id INT IDENTITY(1,1) PRIMARY KEY,
account_id INT  NOT NULL,
phone VARCHAR(15) NOT NULL,
address VARCHAR(255) NOT NULL,
total_price FLOAT NOT NULL,
FOREIGN KEY(account_id) REFERENCES account(id)
);
go
CREATE TABLE order_detail(
id INT IDENTITY(1,1) PRIMARY KEY,
order_id INT NOT NULL,
product_id INT  NOT NULL,
quantity INT NOT NULL,
product_price FLOAT NOT NULL,
FOREIGN KEY(order_id) REFERENCES orders(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);
go
CREATE TABLE brand(
id INT IDENTITY(1,1) PRIMARY KEY,
name VARCHAR(255) NOT NULL,
logo VARCHAR(255) NOT NULL
);
go
CREATE TABLE category_brand(
id  INT IDENTITY(1,1) PRIMARY KEY,
category_id INT NOT NULL,
brand_id INT NOT NULL,
FOREIGN KEY (category_id) REFERENCES category(id),
FOREIGN KEY (brand_id) REFERENCES brand(id)
);
GO
CREATE TABLE account_role(
id INT IDENTITY(1,1) PRIMARY KEY,
account_id INT NOT NULL,
role_id VARCHAR(50) NOT NULL,
FOREIGN KEY (account_id) REFERENCES account(id),
FOREIGN KEY (role_id) REFERENCES role(id)
);
go
CREATE TABLE comment(
id INT IDENTITY(1,1) PRIMARY KEY,
description NVARCHAR(255) NOT NULL,
)
go
CREATE TABLE comment_account(
id INT IDENTITY(1,1) PRIMARY KEY,
account_id INT NOT NULL,
comment_id INT NOT NULL,
FOREIGN KEY(account_id) REFERENCES account(id),
FOREIGN KEY(comment_id) REFERENCES comment(id)
)
CREATE TRIGGER trg_account  ON account 
FOR INSERT
AS DECLARE @account_id INT
SELECT @account_id = account.id FROM account;
SELECT * FROM account
SELECT * FROM account_role;
SELECT * FROM role;