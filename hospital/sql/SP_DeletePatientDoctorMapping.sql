USE ece356;

DELIMITER //
CREATE PROCEDURE DeletePatientDoctorMapping (
		IN patient_id_ INT,
		IN default_doctor_ VARCHAR(6)
	)
    
    BEGIN
    
    DELETE FROM user_patient_view_schema 
	WHERE
		patient_id = patient_id_ AND
        user_name = (SELECT user_name FROM doctor_schema WHERE cpso_number = default_doctor_);
        
	
END //
DELIMITER ;