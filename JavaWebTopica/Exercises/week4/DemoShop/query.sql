DELIMITER $$
DROP PROCEDURE IF EXISTS DemoShop.saveOrder$$
CREATE PROCEDURE DemoShop.saveOrder(in_order_code char(10), in_pro_id integer, in_quantity integer)
BEGIN
    DECLARE `fail` boolean default 0;
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET `fail` = 1;
    BEGIN
        START TRANSACTION ;
        SET autocommit = false;
        INSERT INTO DemoShop.order_from_customer(id, prod_id, quantity, success)
        VALUES (in_order_code, in_pro_id, in_quantity, true);
        SAVEPOINT A;
        INSERT INTO DemoShop.order(id, prod_id, quantity) VALUES (in_order_code, in_pro_id, in_quantity);
        IF in_quantity <= (SELECT quan_in_stock FROM DemoShop.inventory WHERE prod_id = in_pro_id) THEN
            UPDATE DemoShop.inventory SET quan_in_stock = quan_in_stock - in_quantity WHERE prod_id = in_pro_id;
            UPDATE DemoShop.inventory SET sales = sales + in_quantity WHERE prod_id = in_pro_id;
        ELSE
            ROLLBACK TO A;
            UPDATE DemoShop.order_from_customer SET success = false WHERE id = in_order_code;
            COMMIT;
        END IF;
        IF `fail` THEN
            ROLLBACK ;
        ELSE
            COMMIT ;
        END IF;
    END;
END;
$$
DELIMITER ;