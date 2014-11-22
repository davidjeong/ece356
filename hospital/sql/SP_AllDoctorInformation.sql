USE ece356;

DELIMITER //
CREATE PROCEDURE AllDoctorInformation()
BEGIN
	SELECT u.legal_name, d.cpso_number
	FROM 
		user_schema u
	INNER JOIN
		doctor_schema d 
	ON
		u.user_name = d.user_name
	ORDER BY u.legal_name ASC;
END //
DELIMITER ;