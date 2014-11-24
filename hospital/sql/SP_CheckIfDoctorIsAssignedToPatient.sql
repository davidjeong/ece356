USE ece356;

DELIMITER //
CREATE PROCEDURE CheckIfDoctorIsAssignedToPatient (
		IN patient_id_ INT,
		IN cpso_number_ VARCHAR(6)
	)
    BEGIN
    SELECT up.patient_id
		FROM user_patient_view_schema up
		INNER JOIN doctor_schema d ON d.cpso_number = cpso_number_
	WHERE up.user_name = d.user_name
    AND up.patient_id = patient_id_;
    
END //
DELIMITER ;


