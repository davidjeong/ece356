USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPastVisitRecordForDoctor(IN username varchar(32))
	BEGIN
    SELECT
		v.patient_id,
        v.cpso_number,
        v.start_time,
        v.end_time,
        v.surgery_name,
        v.prescription,
        v.comments,
        v.diagnosis
    FROM	
		visit_schema v
	INNER JOIN
		doctor_schema d
        ON d.cpso_number = v.cpso_number
	WHERE 
		d.user_name = username
	AND
		v.end_time < CURTIME()
	ORDER BY v.start_time DESC;
	END //
DELIMITER ;
