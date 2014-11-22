USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllDoctors()
	BEGIN
		SELECT
			*
		FROM	
			doctor_schema d,
			user_schema u
		WHERE
			d.user_name = u.user_name
		ORDER BY u.legal_name;
	END //
DELIMITER ;

