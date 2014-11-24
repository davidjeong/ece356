USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllDoctorWeeklyRecords(IN username varchar(32))
	BEGIN
	SELECT
		v.patient_id,
		v.cpso_number,
		v.start_time,
		v.end_time
	FROM 
		visit_schema v
	INNER JOIN
		doctor_staff_assignment_schema ds
	ON ds.staff = username
	WHERE
		v.cpso_number = ds.cpso_number;
	
	END //
DELIMITER ;
