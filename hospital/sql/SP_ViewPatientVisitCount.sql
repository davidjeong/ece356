USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientVisitCount(IN patient_id_ INT, IN start_time_ DATETIME, IN end_time_ DATETIME)
BEGIN
	SELECT count(distinct v.patient_id)
	FROM 
		visit_schema v 
	WHERE 
		v.patient_id = patient_id_ and v.start_time>=start_time_ and v.end_time <=end_time_;
END //
DELIMITER ;