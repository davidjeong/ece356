USE ece356;

DELIMITER //
CREATE PROCEDURE CountDoctorVisits(IN doctor_number_ varchar(32), IN time_period_ datetime)
BEGIN
	SELECT count(distinct v.patient_id)
	FROM 
		visit_schema v 
	WHERE 
		v.cpso_number = doctor_number_ AND v.start_time >= time_period_;
END //
DELIMITER ;