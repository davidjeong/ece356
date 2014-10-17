USE ece356;

DELIMITER //
CREATE PROCEDURE VerifyUserCredentials(IN user_name_ VARCHAR(32), IN user_password_ VARCHAR(32))
	BEGIN
    SELECT 
		u.user_type
	FROM
		user_schema u
	WHERE
		u.user_name = user_name_
        AND u.user_password = user_password_;
	END //
DELIMITER ;