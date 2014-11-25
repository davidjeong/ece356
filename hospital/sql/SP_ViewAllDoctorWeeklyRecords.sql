USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllDoctorWeeklyRecords()
	BEGIN
	SELECT
		v.patient_id,
        u.legal_name AS patient_legal_name,
		v.cpso_number,
        uu.legal_name AS doctor_legal_name,
		v.start_time,
		v.end_time
	FROM 
		visit_schema v
	INNER JOIN patient_schema p
		ON v.patient_id = p.patient_id
	INNER JOIN
		user_schema u
        ON u.user_name = p.user_name
	INNER JOIN doctor_schema d
		ON v.cpso_number = d.cpso_number
	INNER JOIN user_schema uu
		ON d.user_name = uu.user_name;
	
	END //
DELIMITER ;
