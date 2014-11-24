USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllStaff()
	BEGIN
		SELECT
			u.user_name,
			u.legal_name
		FROM	
			user_schema u
		WHERE
			u.user_type = "staff"
		ORDER BY u.legal_name;
	END //
DELIMITER ;
