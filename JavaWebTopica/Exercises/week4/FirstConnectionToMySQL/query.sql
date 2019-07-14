DELIMITER $$
CREATE PROCEDURE GetAllStudents()
BEGIN
    SELECT * FROM student;
END; $$
DELIMITER ;

CALL GetAllStudents();