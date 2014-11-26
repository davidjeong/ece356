USE ece356;

DELIMITER //
CREATE PROCEDURE DeleteDoctorPatientRightsForPatient(
		IN patient_id_ INT,
		IN default_doctor_ VARCHAR(32)
	)
    BEGIN
		DELETE up
		FROM user_patient_view_schema up 
			INNER JOIN user_schema us ON up.user_name = us.user_name
		WHERE up.patient_id = patient_id_
			AND up.user_name != default_doctor_
			AND us.user_type = "doctor";
	END //
DELIMITER ;

