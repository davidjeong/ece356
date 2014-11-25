USE ece356;

DELIMITER //
CREATE PROCEDURE CheckPatientDoctorMapping (
		IN patient_id_ INT,
        IN default_doctor_ VARCHAR(6)
	)
    
    BEGIN
    
   SELECT COUNT(up.patient_id) AS occurrence
	  FROM user_patient_view_schema up
   INNER JOIN doctor_schema d
	  ON d.cpso_number = default_doctor_
   WHERE up.user_name = d.user_name
   AND patient_id = patient_id_;
	
END //
DELIMITER ;