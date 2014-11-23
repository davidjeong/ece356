USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPastVisitRecord(IN username varchar(32))
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
		user_patient_view_schema up
        ON up.patient_id = v.patient_id
	WHERE 
		up.user_name = username
	AND
		v.end_time < CURTIME()
	ORDER BY v.start_time DESC;
	END //
DELIMITER ;
