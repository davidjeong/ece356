USE ece356;

DELIMITER //
CREATE PROCEDURE ViewUpcomingDoctorWeeklyRecords(IN cpso_number_ VARCHAR(6))
	BEGIN
	SELECT
		v.patient_id,
		v.cpso_number,
		v.start_time,
		v.end_time
	FROM 
		visit_schema v
	WHERE
		v.cpso_number = cpso_number_
        AND v.start_time >= NOW();
	
	END //
DELIMITER ;