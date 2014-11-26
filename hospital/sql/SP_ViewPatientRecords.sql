USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientRecords(IN patient_id_ INT)
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
	WHERE
		v.patient_id = patient_id_
	ORDER BY v.start_time DESC;
	END //
DELIMITER ;