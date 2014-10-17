USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientRecords(IN patient_id_ INT)
	BEGIN
    SELECT 
		v.start_time,
        v.end_time,
        v.comments,
        v.surgery_name,
		v.prescription
	FROM
		visit_schema v
	WHERE
		v.patient_id = patient_id_;
	END //
DELIMITER ;