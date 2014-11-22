USE ece356;

DELIMITER //
CREATE PROCEDURE ViewDoctorsForStaff(IN staff_name_ varchar(32))
BEGIN
	SELECT *
	FROM doctor_schema d
		INNER JOIN doctor_staff_assignment_schema ds ON ds.cpso_number = d.cpso_number
		INNER JOIN user_schema u ON ds.user_name = u.user_name
	WHERE ds.staff = staff_name_
	ORDER BY u.legal_name ASC;
END //
DELIMITER ;
