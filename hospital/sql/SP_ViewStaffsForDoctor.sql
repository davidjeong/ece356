USE ece356;

DELIMITER //
CREATE PROCEDURE ViewStaffsForDoctor(IN cpso_number_ varchar(32))
BEGIN
	SELECT *
	FROM user_schema u
		INNER JOIN doctor_staff_assignment_schema ds ON u.user_name = ds.staff
	WHERE ds.cpso_number = cpso_number_
	ORDER BY u.legal_name ASC;
END //
DELIMITER ;
