USE ece356

DELIMITER //
CREATE PROCEDURE InsertNewPatientInformation 
		(
			IN user_name_ VARCHAR(32),
            IN default_doctor_ VARCHAR(6),
            IN health_status_ VARCHAR(32),
            IN health_card_number_ VARCHAR(32),
            IN sin_number_ VARCHAR(32),
            IN phone_number_ VARCHAR(16),
            IN address_ VARCHAR(64)
		)
	BEGIN
    INSERT INTO patient_schema
    (
		user_name,
        default_doctor,
        health_status,
        health_card_number,
        sin_number,
        phone_number,
        address
	)
    VALUES
    (
		user_name_,
        default_doctor_,
        health_status_,
        health_card_number_,
        sin_number_,
        phone_number_,
        address_
	);
END //
DELIMITER ;
