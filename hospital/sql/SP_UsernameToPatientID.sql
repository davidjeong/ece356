USE ece356;

DELIMITER //
CREATE PROCEDURE UsernameToPatientID(IN user_name_ varchar(32))
BEGIN
	SELECT d.patient_id 
	FROM 
		user_schema u 
	JOIN 
		patient_schema d ON u.user_name = d.user_name
	WHERE 
		u.user_type = "patient" AND u.user_name = user_name_;
END //
DELIMITER ;