USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewUserRecord (
		IN legal_name_ VARCHAR(64),
		IN user_name_ VARCHAR(32),
        IN user_password_ VARCHAR(32),
        IN user_type_ enum('doctor','patient','staff','legal','finance')

	)
    BEGIN
    INSERT INTO user_schema 
    (
		legal_name,
		user_name,
        user_password,
        user_type
	)
    VALUES
	(
        legal_name_,
		user_name_,
        user_password_,
        user_type_ 
	);
END //
DELIMITER ;