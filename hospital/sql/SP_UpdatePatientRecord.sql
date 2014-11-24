USE ece356;

DELIMITER //
CREATE PROCEDURE UpdatePatientRecord (
		IN patient_id_ INT,
		IN Health_Card_Number_ VARCHAR(32),
        IN Sin_Number_ VARCHAR(32),
        IN Phone_Number_ VARCHAR(32),
        IN Address_ VARCHAR(64)
	)
    BEGIN
    UPDATE patient_schema SET 
		patient_id = patient_id_,
        health_card_number = Health_Card_Number_,
        sin_number = Sin_Number_,
        phone_number = Phone_Number_,
        address = Address_
	WHERE
		patient_id = patient_id_;
END //
DELIMITER ;


