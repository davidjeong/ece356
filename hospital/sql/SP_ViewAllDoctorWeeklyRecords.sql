USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllDoctorWeeklyRecords()
	BEGIN
	SELECT
		v.patient_id,
		v.cpso_number,
		v.start_time,
		v.end_time
	FROM 
		visit_schema v;
	
	END //
DELIMITER ;
