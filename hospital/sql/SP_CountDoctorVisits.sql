USE ece356;

DELIMITER //
CREATE PROCEDURE CountDoctorVisits(IN doctor_number_ varchar(32), IN start_time_ datetime, IN end_time_ datetime)
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
		v.cpso_number = doctor_number_ AND start_time_ <= v.start_time AND v.end_time <= end_time_;
END //
DELIMITER ;