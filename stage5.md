# Этап 5
Разработать 3 хранимых процедуры, 3 функции и 3 триггера. Дать описание разработанных элементов. В реализованном коде должны быть использованы как минимум три курсора разного типа.

## Создание процедур

``` sql
CREATE PROCEDURE ingredient_name AS
BEGIN
	SELECT p.name, i.pizza_id, i.ingredient_id, i.weight_in_grams 
	FROM ingredient i 
	INNER JOIN product p 
	ON i.ingredient_id = p.product_id
END;

EXEC ingredient_name
```
``` sql
CREATE PROCEDURE required_ingredients_for_pizza @pizzaName NVARCHAR(50) AS
BEGIN
	SELECT p.ingredients
	FROM ingredients_for_pizza AS p
	WHERE p.name = @pizzaName
END

EXEC required_ingredients_for_pizza @pizzaName = 'Margarita'
```
``` sql
CREATE PROCEDURE change_current_price @productId int, @newPrice MONEY AS
BEGIN
	UPDATE product 
	SET current_price = @newPrice
	WHERE product_id = @productId
END

EXEC change_current_price @productId = 1, @newPrice = 110;
```
## Создание тригеров

``` sql
CREATE TRIGGER product_update
ON product AFTER UPDATE AS 
BEGIN
	DECLARE @id int
	DECLARE @oldPrice money
	DECLARE @newPrice money
	SELECT @id = (SELECT product_id FROM deleted)
	SELECT @oldPrice = (SELECT current_price FROM deleted)
	SELECT @newPrice = (SELECT current_price FROM inserted)

	DECLARE @message NVARCHAR(100)
	SELECT @message = CONCAT('product updated id = ', @id, ', old price = ', @oldPrice, ', new price = ', @newPrice);
	RAISERROR(@message,1,1);
END
```
``` sql
CREATE TRIGGER trigger_product_name_and_price
ON product_name_and_price 
INSTEAD OF DELETE, INSERT, UPDATE
AS RAISERROR('You cannot change this view', 1, 1);

UPDATE product_name_and_price 
	SET price = 250
	WHERE name = 'Three cheeses'
```
``` sql
CREATE TRIGGER invoice_insert
ON invoice
AFTER INSERT AS
BEGIN
	DECLARE @product_id int
	DECLARE @number int
	DECLARE @price money

	SELECT @product_id = (SELECT product_id FROM inserted)
	SELECT @number = (SELECT number FROM inserted)
	SELECT @price = (SELECT price_per_piece FROM inserted)

	DECLARE @productName NVARCHAR(50) = (SELECT name FROM product where product.product_id = @product_id)
	DECLARE @message NVARCHAR(100) = CONCAT('new products arrived: ', @productName, ', number: ',  @number, ', price: ', @number * @price);
	RAISERROR(@message, 1, 1);
END

INSERT INTO invoice
	VALUES (11, 3, 'ООО Продуктс', '2018-10-22 8:11:9', 2, 2000, 30, '2018-10-21 22:22:22')
```

## Создание курсоров

``` sql
DECLARE cursor_for_products SCROLL CURSOR FOR
SELECT name, current_price FROM product
ORDER BY current_price

OPEN cursor_for_products;
FETCH FIRST FROM cursor_for_products;
FETCH LAST FROM cursor_for_products;
FETCH ABSOLUTE 2 FROM cursor_for_products;
FETCH RELATIVE 3 FROM cursor_for_products; 
CLOSE cursor_for_products;
```

``` sql
DECLARE cursor_for_orders CURSOR FORWARD_ONLY FOR
SELECT order_id, order_price FROM orders
ORDER BY order_price

OPEN cursor_for_orders

DECLARE @id int, @order_price money, @count int = 0;
FETCH NEXT FROM cursor_for_orders
INTO @id, @order_price
WHILE @@FETCH_STATUS = 0
BEGIN
	IF @order_price >= 1000
		SELECT @count = @count + 1
	
	FETCH NEXT FROM cursor_for_orders
	INTO @id, @order_price
END
PRINT @count

CLOSE cursor_for_orders
```
``` sql
DECLARE cursor_for_ingredient CURSOR STATIC FOR
SELECT pizza_id, ingredient_id, weight_in_grams FROM ingredient

OPEN cursor_for_ingredient

DECLARE @id int, @ingredient_id int, @weight_in_grams nvarchar(50), @count int = 0
FETCH NEXT FROM cursor_for_ingredient
INTO @id, @ingredient_id, @weight_in_grams;

DECLARE @pizza_id int = 1;
WHILE @@FETCH_STATUS = 0
BEGIN
	IF @id = @pizza_id
		SELECT @count = @count + 1
	
	FETCH NEXT FROM cursor_for_ingredient
	INTO @id, @ingredient_id, @weight_in_grams
END
print @count
CLOSE cursor_for_ingredient
```