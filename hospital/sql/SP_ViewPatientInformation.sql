USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientInformation (
		IN patient_id_ INT
	)
    
    BEGIN
    
    SELECT 
		p.user_name,
		p.default_doctor,
		p.health_status,
		p.health_card_number,
		p.sin_number,
		p.phone_number,
		p.address
    FROM patient_schema p
    WHERE p.patient_id = patient_id_;
        
	
END //
DELIMITER ;