SET autocommit = 0;
INSERT INTO FirstSchema.subject VALUES ('PE2020', 'Badminton 2', 0,NULL);
COMMIT ;
UPDATE FirstSchema.subject SET note = 'noted 1' WHERE id = 'PE2019';
SAVEPOINT A;
UPDATE FirstSchema.subject SET note = 'noted 1' WHERE id = 'IT1111';
SAVEPOINT B;
UPDATE FirstSchema.subject SET note = 'noted 1' WHERE id = 'IT2222';
SELECT * FROM FirstSchema.subject;
ROLLBACK TO B;
SELECT * FROM FirstSchema.subject;
ROLLBACK TO A;
SELECT * FROM FirstSchema.subject;

# example of commit, rollback and savepoint with procedure
DELIMITER $$
DROP PROCEDURE IF EXISTS FirstSchema.testCommit$$
CREATE PROCEDURE FirstSchema.testCommit()
BEGIN
    declare `fail` boolean default 0;
    declare continue handler for sqlexception set `fail` = 1;
    BEGIN
        START TRANSACTION;
        SET autocommit = false;
        savepoint start_savepoint;
        BEGIN
            SET AUTOCOMMIT = false;
            UPDATE FirstSchema.subject SET note = 'noted 1' WHERE id = 'PE2020';
        END;
        UPDATE FirstSchema.subject SET id = 'IT2121' WHERE name = 'Database Lab';
        IF `fail` THEN
            ROLLBACK TO start_savepoint;
        ELSE COMMIT ;
        END IF;
    END;
END;
$$ DELIMITER ;

CALL FirstSchema.testCommit();
SELECT * FROM FirstSchema.subject;