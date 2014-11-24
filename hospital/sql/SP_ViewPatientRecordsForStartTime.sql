USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientRecordsForStartTime(IN cpso_number_ VARCHAR(6), IN start_time_ DATETIME)
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
		v.cpso_number = cpso_number_
        AND v.start_time = start_time_;
	END //
DELIMITER ;