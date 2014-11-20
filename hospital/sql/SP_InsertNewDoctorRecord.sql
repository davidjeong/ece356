USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewDoctorRecord (
		IN user_name_ VARCHAR(32),
    	IN cpso_number_ VARCHAR(6),
    	IN department_ VARCHAR(32),
	)
    BEGIN
    INSERT INTO doctor_schema 
    (
		user_name,
		cpso_number,
		department
	)
    VALUES
	(
		user_name_,
		cpso_number_,
		department_
	);
END //
DELIMITER ;
