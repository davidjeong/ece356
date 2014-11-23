USE ece356;

DELIMITER //
CREATE PROCEDURE UniquePatientsGivenDoctor(IN doctor_number_ varchar(32))
BEGIN
	SELECT count(distinct v.patient_id)
	FROM 
		visit_schema v 
	WHERE 
		v.cpso_number = doctor_number_;
END //
DELIMITER ;