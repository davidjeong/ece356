USE ece356;

DELIMITER //
CREATE PROCEDURE ViewDoctorsForStaff(IN user_name_ varchar(32))
BEGIN
SELECT u.legal_name, d.user_name, d.cpso_number, d.department
	FROM doctor_schema d
INNER JOIN user_schema u ON u.user_name = d.user_name
INNER JOIN doctor_staff_assignment_schema ds ON ds.cpso_number = d.cpso_number
	WHERE ds.staff = user_name_
ORDER BY u.legal_name ASC;
END //
DELIMITER ;