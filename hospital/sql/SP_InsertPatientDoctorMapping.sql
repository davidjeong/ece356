USE ece356;

DELIMITER //
CREATE PROCEDURE InsertPatientDoctorMapping (
		IN patient_id_ INT,
        IN default_doctor_ VARCHAR(32)
	)
    
    BEGIN
    
    INSERT INTO user_patient_view_schema ( user_name, patient_id) 
    VALUES (default_doctor_, patient_id_);
	
END //
DELIMITER ;