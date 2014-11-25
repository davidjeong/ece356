USE ece356;

DELIMITER //
CREATE PROCEDURE UpdatePatientRecord (
		IN patient_id_ INT,
        IN default_doctor_ VARCHAR(6),
        IN health_status_ VARCHAR(32),
        IN health_card_number_ VARCHAR(32),
        IN sin_number_ VARCHAR(32),
        IN phone_number_ VARCHAR(16),
        IN address_ VARCHAR(64)
	)
	BEGIN
    UPDATE patient_schema SET 
		default_doctor = default_doctor_,
        health_status = health_status_,
        health_card_number = health_card_number_,
        sin_number = sin_number_,
        phone_number = phone_Number_,
        address = Address_
	WHERE
		patient_id = patient_id_;
END //
DELIMITER ;


