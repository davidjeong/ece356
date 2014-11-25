USE ece356;

DELIMITER //
CREATE PROCEDURE StaffUpdatePatientRecord (
		IN patient_id_ INT,
		IN Default_Doctor_ VARCHAR(6),
		IN Health_Card_Number_ VARCHAR(32),
        IN Health_Status_ VARCHAR(32),
        IN Phone_Number_ VARCHAR(16),
        IN Address_ VARCHAR(64)
	)
    BEGIN
    UPDATE patient_schema SET 
		default_doctor = Default_Doctor_,
		patient_id = patient_id_,
        health_card_number = Health_Card_Number_,
		health_status = Health_Status_,
        phone_number = Phone_Number_,
        address = Address_
	WHERE
		patient_id = patient_id_;
	
        
END //
DELIMITER ;


Call StaffUpdatePatientRecord ('1','000000','123','123','123','123')