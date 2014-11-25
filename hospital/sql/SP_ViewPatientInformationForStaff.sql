USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientInformationForStaff ()
    
    BEGIN
    
    SELECT 
		p.patient_id,
		p.user_name,
        u.legal_name AS 'patient_legal_name',
		p.default_doctor,
        uu.legal_name AS 'doctor_legal_name',
		p.health_status,
		p.health_card_number,
		p.sin_number,
		p.phone_number,
		p.address
	FROM patient_schema p
    INNER JOIN user_schema u
		ON u.user_name = p.user_name
    INNER JOIN doctor_schema d
		ON d.cpso_number = p.default_doctor
	INNER JOIN user_schema uu
		ON d.user_name = uu.user_name

    ORDER BY u.legal_name ASC;
	
END //
DELIMITER ;