USE ece356;

DELIMITER //
CREATE PROCEDURE UniquePatientsGivenDoctor(IN doctor_number_ varchar(32), IN start_time_ datetime, IN end_time_ datetime)
BEGIN
	SELECT count(distinct v.patient_id)
	FROM 
		visit_schema v 
	WHERE 
		v.cpso_number = doctor_number_ AND start_time_ <= v.start_time AND v.end_time <= end_time_;
END //
DELIMITER ;