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
		"leo zhao",
        "doctor",
        "GREAT",
        "007",
        "007",
        "905-884-2588",
        "6 lanebrooke"
	);
END //
DELIMITER ;