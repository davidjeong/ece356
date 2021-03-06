USE ece356

DELIMITER //
CREATE PROCEDURE InsertNewPatientRecord 
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
    
    INSERT INTO user_patient_view_schema
    (
		user_name,
        patient_id
    ) VALUES (
		(SELECT user_name FROM doctor_schema WHERE cpso_number = default_doctor_),
        (SELECT patient_id FROM patient_schema WHERE user_name = user_name_)
	);
    
END //
DELIMITER ;
