USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllPatientsForDoctor(IN cpso_number_ varchar(6))
	BEGIN
    SELECT
		p.patient_id,
        u.legal_name
    FROM	
		patient_schema p
	INNER JOIN
		user_schema u
        ON u.user_name = p.user_name
	WHERE 
		p.default_doctor = cpso_number_
	ORDER BY u.legal_name ASC;
	END //
DELIMITER ;
