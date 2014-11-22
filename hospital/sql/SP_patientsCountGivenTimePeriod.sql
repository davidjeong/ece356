USE ece356;

DELIMITER //
CREATE PROCEDURE patientsCountGivenTimePeriod(IN doctor_number_ varchar(32), IN time_period_ datetime)
BEGIN
	SELECT count(distinct v.patient_id)
	FROM 
		visit_schema v 
	WHERE 
		v.cpso_number = doctor_number_ AND start_end >= time_period_;
END //
DELIMITER ;