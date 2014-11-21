USE ece356;

DELIMITER //
CREATE PROCEDURE UsernameToCpsonumber(IN user_name_ varchar(32))
BEGIN
	SELECT d.cpso_number 
	FROM 
		user_schema u 
	JOIN 
		doctor_schema d ON u.user_name = d.user_name
	WHERE 
		u.user_type = "doctor" AND u.user_name = user_name_;
END //
DELIMITER ;