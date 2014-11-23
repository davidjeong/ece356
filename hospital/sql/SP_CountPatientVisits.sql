USE ece356;

DELIMITER //
CREATE PROCEDURE CountPatientVisits(IN patient_id_ INT, IN start_time_ DATETIME, IN end_time_ DATETIME)
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
		v.patient_id = patient_id_ and v.start_time>=start_time_ and v.end_time <=end_time_;
	END //
DELIMITER ;