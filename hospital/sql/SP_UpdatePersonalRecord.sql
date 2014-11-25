USE ece356;

DELIMITER //
CREATE PROCEDURE UpdatePersonalRecord (
		IN patient_id_ INT,
        IN phone_number_ VARCHAR(32),
        IN address_ VARCHAR(64)
	)
	BEGIN
    UPDATE patient_schema SET 
        phone_number = Phone_Number_,
        address = Address_
	WHERE
		patient_id = patient_id_;
END //
DELIMITER ;


