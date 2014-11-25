USE ece356;

DELIMITER //
CREATE PROCEDURE ViewUpcomingVisitRecordForStaff(IN username varchar(32))
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
		patient_schema p
        ON p.patient_id = v.patient_id
    INNER JOIN
		doctor_staff_assignment_schema ds
        ON ds.cpso_number = v.cpso_number
	WHERE 
		ds.staff = username AND
		v.start_time >= CURTIME()
	ORDER BY v.cpso_number, v.start_time ASC;
	END //
DELIMITER ;
