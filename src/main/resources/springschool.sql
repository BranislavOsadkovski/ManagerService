DROP DATABASE IF EXISTS `test`;
CREATE DATABASE `test`; 
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(50) NOT NULL,
age int NOT NULL,email varchar(30) NOT NULL,image mediumblob)engine=INNODB;

DROP PROCEDURE IF EXISTS `getRecord`;
DELIMITER $$
CREATE PROCEDURE  `getRecord` (
IN in_id INTEGER, 
OUT out_name VARCHAR(20),
OUT out_age  INTEGER,
OUT out_email VARCHAR(50),
OUT out_image BLOB)
BEGIN
   SELECT name, age, email,image
   INTO out_name, out_age,out_email,out_imagestudent
   FROM Student where id = in_id;
END $$
DELIMITER ;  

DROP PROCEDURE IF EXISTS `getRecordByName`;
DELIMITER $$
CREATE PROCEDURE  `getRecordByName` (
IN in_name VARCHAR(20),
OUT out_id INTEGER,
OUT out_age  INTEGER,
OUT out_email VARCHAR(50),
OUT out_image BLOB)
BEGIN
   SELECT id, age, email,image
   INTO out_id, out_age,out_email,out_image
   FROM Student where name= in_name;
END $$
DELIMITER ;  
 