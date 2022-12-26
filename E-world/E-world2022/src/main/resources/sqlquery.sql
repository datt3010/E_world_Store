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
password VARCHAR(100),
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
go
CREATE TABLE blog(
id INT IDENTITY(1,1) PRIMARY KEY,
name NVARCHAR(100) NOT NULL,
description NVARCHAR(255),
image VARCHAR(255) NOT NULL,
status VARCHAR(50) NOT NULL
)
go
CREATE TABLE cart_items(
id INT IDENTITY(1,1) PRIMARY KEY,
account_id INT NOT NULL,
product_id INT NOT NULL,
quantity INT,
FOREIGN KEY(account_id) REFERENCES account(id),
FOREIGN KEY(product_id) REFERENCES product(id)
)
go
CREATE TRIGGER trg_account  ON account 
AFTER INSERT AS
DECLARE @account_id INT
SELECT @account_id = id FROM account

INSERT INTO account_role(account_id,role_id) VALUES(@account_id,'3');
SELECT * FROM account
SELECT * FROM account_role;
SELECT * FROM role;
SELECT * FROM order_detail;
SELECT * FROM orders;
DELETE FROM account WHERE id=4;

INSERT INTO role(id,name) VALUES('1','Admin'),('2','Staff'),('3','Custom');

SELECT *  FROM account_role ar inner join role r on ar.role_id = r.id

SELECT * FROM account_role ar inner join account a on ar.account_id = a.id
WHERE ar.account_id = 28

SELECT * FROM account a inner join account_role ar on ar.account_id = a.id
WHERE ar.role_id =3;

SELECT * FROM product;

UPDATE account  SET status = 'ACTIVE' WHERE id=28;	

SELECT * FROM blog;

select count(p.name) from product p where p.category_id IN (select cb.category_id from category_brand cb where cb.brand_id=1) ;

SELECT * FROM category

SELECT * FROM category_brand

select count (a.id) from account a inner join account_role ar on ar.account_id = a.id 
where ar.role_id=3

SELECT * FROM cart_items ci inner join account a on a.id = ci.account_id where ci.account_id = a.id;

select top 3 p.name, p.price from product p inner join order_detail od on od.product_id = p.id
group by p.name
order by sum(od.quantity) desc

select * from orders where account_id = 34
select * from order_detail

select count(a.id) from account a inner join  account_role ar on ar.account_id = a.id
where ar.role_id=3 and MONTH(a.create_at) =1

select distinct count(p.id) from product p inner join order_detail od on od.product_id = p.id
where od.order_id IN (select os.id FROM orders os where MONTH(os.created_at) = 12)

select sum(o.total_price) as 'Doanh thu tháng 12' from orders o where MONTH(o.created_at)=12

select distinct *  from category c inner join product p on p.category_id = c.id
where p.id in(select od.product_id  from order_detail od inner join orders o on o.id= od.order_id where MONTH(o.created_at)=12);

select sum(o.total_price) from orders o where MONTH(o.created_at) = 12;

select distinct * from product p inner join order_detail od on od.product_id = p.id
group by p.id
order by od.quantity asc;

SELECT
       SUM(o.total_price) AS transaction_amount
FROM account a inner join orders o on o.account_id = a.id
WHERE o.account_id IN
      (
          SELECT
                 o.account_id
          FROM orders o
  
      )
GROUP BY o.account_id,a.first_name,a.last_name
ORDER BY o.account_id

select * from account	