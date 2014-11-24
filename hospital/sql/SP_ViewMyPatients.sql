USE ece356;

DELIMITER //
CREATE PROCEDURE ViewMyPatients(IN cpso_number_ INT)
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
	WHERE
		p.default_doctor = cpso_number_
	ORDER BY u.legal_name ASC;
	END //
DELIMITER ;