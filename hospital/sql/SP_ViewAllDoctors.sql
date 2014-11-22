USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllDoctors()
	BEGIN
		SELECT
			*
		FROM	
			doctor_schema d
		ORDER BY d.legal_name;
	END //
DELIMITER ;

