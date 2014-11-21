USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAssignedDoctor(IN cpso_number_ INT)
	BEGIN
    SELECT
		p.default_doctor,
        p.health_status,
        p.user_name,
        u.legal_name
	FROM
		patient_schema p
	INNER JOIN
		user_schema u
        ON u.user_name = p.user_name
	WHERE
		p.default_doctor = cpso_number_;
	END //
DELIMITER ;