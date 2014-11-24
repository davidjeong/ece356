USE ece356;

DELIMITER //
CREATE PROCEDURE ViewDoctorsForPatient(IN patient_id_ varchar(32))
BEGIN
	SELECT *
	FROM user_patient_view_schema up
		INNER JOIN doctor_schema d ON d.user_name = up.user_name
		INNER JOIN user_schema u ON u.user_name = up.user_name
	WHERE up.patient_id = patient_id_
	ORDER BY u.legal_name ASC;
END //
DELIMITER ;
