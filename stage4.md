# Этап 4

Создать скрипт создания БД и таблиц. Дать примеры заполнения данными. Представить три представления с описаниями.

## Создание БД
``` sql
CREATE DATABASE DB_Design
```

## Создание таблиц

``` sql
CREATE TABLE category(
	category_id int NOT NULL,
	name nvarchar(50)

	CONSTRAINT pk_category PRIMARY KEY (category_id)
)
```

``` sql
CREATE TABLE product(
	product_id int NOT NULL,
	category_id int NOT NULL,
	name nvarchar(50),
	current_price money,
	shelf_life_in_hours int,
	

	CONSTRAINT pk_product PRIMARY KEY (product_id),

	CONSTRAINT fk_product_category_id
		FOREIGN KEY (category_id)
		REFERENCES category (category_id) 
)
```
``` sql
CREATE TABLE ingredient(
	pizza_id int NOT NULL,
	ingredient_id int NOT NULL,
	weight_in_grams int NOT NULL

	CONSTRAINT fk_ingredient_pizza_id
		FOREIGN KEY (pizza_id)
		REFERENCES product (product_id),
	
	CONSTRAINT fk_ingredient_ingredient_id
		FOREIGN KEY (ingredient_id)
		REFERENCES product (product_id)
)
```
``` sql
CREATE TABLE orders(
	order_id int NOT NULL,
	customer_name nvarchar(50),
	delivery_address nvarchar(50),
	customer_number nvarchar(20),
	order_price money,
	date_and_time_of_receipt_of_order datetime2,

	CONSTRAINT pk_orders PRIMARY KEY (order_id)

)
```
``` sql
CREATE TABLE list(
	order_id int NOT NULL,
	product_id int NOT NULL,
	number int,
	price_per_piece money

	CONSTRAINT fk_list_order_id
		FOREIGN KEY (order_id)
		REFERENCES orders (order_id),

	CONSTRAINT fk_list_product_id
		FOREIGN KEY (product_id)
		REFERENCES product (product_id),
)
```
``` sql
CREATE TABLE invoice(
	invoice_id int NOT NULL,
	product_id int NOT NULL,
	name_of_supplier nvarchar(50),
	date_and_time_of_delivery datetime2,
	number int,
	weight_in_grams int,
	price_per_piece money,
	date_and_time_of_manufacture datetime2,

	CONSTRAINT fk_invoice_product_id
		FOREIGN KEY (product_id)
		REFERENCES product (product_id),

)
```

## Заполнение таблиц

``` sql
INSERT INTO category
	VALUES (1, 'pizza'),
			(2, 'ingredient'),
			(3, 'carbonated drink');
```
``` sql
INSERT INTO product (product_id, category_id, name, current_price, shelf_life_in_hours) 
	VALUES (1, 1, 'Three cheeses', 149.90, 10),
			(2, 1, 'Margarita', 200, 10),
			(3, 2, 'Dough', 20, 48),
			(4, 2, 'Tomato sauce', 10, 10),
			(5, 2, 'Mozzarella cheese', 30, 168),
			(6, 2, 'Tomato', 20, 1080),
			(7, 2, 'Parmesan cheese', 20, 168),
			(8, 2, 'Dorblu', 25, 168),
			(9, 3, 'Coca-cola', 70, 4464),
			(10, 3, 'Mountain Dew', 80, 4464);
```
``` sql
INSERT INTO ingredient (pizza_id, ingredient_id, weight_in_grams)
	VALUES (1, 3, 300),
			(1, 5, 70),
			(1, 6, 100),
			(1, 7, 70),
			(1, 8, 70),
			(2, 3, 300),
			(2, 4, 130),
			(2, 5, 180),
			(2, 6, 100);
```
``` sql
INSERT INTO invoice	(invoice_id, product_id, name_of_supplier,
					date_and_time_of_delivery, number, weight_in_grams, 
					price_per_piece, date_and_time_of_manufacture)
		VALUES (1, 3, 'ООО Продуктс', '2018-10-22 8:11:9', null, 2000, 30, '2018-10-21 22:22:22'),
				(1, 4, 'ООО Продуктс', '2018-10-22 8:11:9', null, 500, 20, '2018-10-20 21:21:21'),
				(1, 5, 'ООО Продуктс', '2018-10-22 8:11:9', null, 700, 400, '2018-10-20 21:21:21'),
				(1, 6, 'ООО Продуктс', '2018-10-22 8:11:9', null, 1000, 60, '2018-10-20 21:21:21'),
				(1, 7, 'ООО Продуктс', '2018-10-22 8:11:9', null, 500, 300, '2018-10-20 21:21:21'),
				(1, 8, 'ООО Продуктс', '2018-10-22 8:11:9', null, 500, 150, '2018-10-20 21:21:21'),
				(1, 9, 'ООО Продуктс', '2018-10-22 8:11:9', 5, null, 40, '2018-10-20 21:21:21'),
				(1, 10, 'ООО Продуктс', '2018-10-22 8:11:9', 10, null, 45, '2018-10-20 21:21:21');
```
``` sql
INSERT INTO orders (order_id, customer_name, delivery_address, customer_number, order_price, date_and_time_of_receipt_of_order)
		VALUES (1, 'Алишер', 'ул. Пушкина д.1', '89612708265', 990, '2018-10-22 15:10:9'),
				(2, 'Вася', null, null, 1000, '2018-10-22 15:20:9');
```
``` sql
INSERT INTO list (order_id, product_id, number, price_per_piece)
		VALUES (1, 1, 1, 400),
				(1, 2, 1, 450),
				(1, 9, 2, 70),
				(2, 1, 2, 400),
				(2, 10, 3, 80.1);
```

## Создание представлений 

``` sql
CREATE VIEW ingredients_for_pizza (name, ingredients) AS
SELECT p.name, STRING_AGG(concat(i.name, ':', i.weight_in_grams), ', ') as ingredients
FROM product AS p INNER JOIN 
	(SELECT i.pizza_id, i.ingredient_id, p.name, i.weight_in_grams 
	FROM ingredient i 
	INNER JOIN product p ON i.ingredient_id = p.product_id) AS i 
		ON p.product_id = i.pizza_id
GROUP BY p.name;

SELECT * FROM ingredients_for_pizza
```
``` sql
CREATE VIEW product_name_and_price (name, price) AS
SELECT name, current_price
FROM product

SELECT * FROM product_name_and_price
```

``` sql
CREATE VIEW products_in_order (order_id, products, order_price) AS
SELECT o.order_id, STRING_AGG(concat(p.name, ':', l.number), ', '), SUM(l.number * l.price_per_piece)
FROM orders AS o INNER JOIN list AS l ON o.order_id = l.order_id
		INNER JOIN product AS p ON l.product_id = p.product_id
GROUP BY o.order_id;

SELECT * FROM products_in_order
```