USE ece356;

DELIMITER //
CREATE PROCEDURE ViewOtherPatients(IN cpso_number_ INT)
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
	INNER JOIN user_patient_view_schema up
		ON up.patient_id = p.patient_id
	WHERE up.user_name = (SELECT u.user_name FROM user_schema u INNER JOIN doctor_schema d ON u.user_name = d.user_name WHERE d.cpso_number = cpso_number_)
    AND p.default_doctor != cpso_number_
	ORDER BY u.legal_name ASC;
	END //
DELIMITER ;