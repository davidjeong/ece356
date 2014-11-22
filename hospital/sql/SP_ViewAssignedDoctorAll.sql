USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAssignedDoctorAll ()
	BEGIN
    SELECT
		p.patient_id,
		u.legal_name AS patient_legal_name,
        uu.legal_name AS doctor_legal_name,
        p.health_status
	FROM
		patient_schema p
	INNER JOIN
		user_schema u
        ON u.user_name = p.user_name
	INNER JOIN 
		doctor_schema d
        ON d.cpso_number = p.default_doctor
	INNER JOIN 
		user_schema uu
        ON uu.user_name = d.user_name
	ORDER BY u.legal_name ASC;
	END //
DELIMITER ;