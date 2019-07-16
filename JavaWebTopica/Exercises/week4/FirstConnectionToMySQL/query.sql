DELIMITER $$
CREATE PROCEDURE GetAllStudents()
BEGIN
    SELECT * FROM student;
END; $$
DELIMITER ;

CALL GetAllStudents();

DROP PROCEDURE GetAllStudents;
DELIMITER $$
DROP PROCEDURE IF EXISTS GetAllStudentsByClass$$
CREATE PROCEDURE GetAllStudentsByClass(in_clazz varchar(45))
BEGIN
    SELECT * FROM student WHERE student.clazz = in_clazz;
END; $$
DELIMITER ;

CALL GetAllStudentsByClass('ICT02-K61');