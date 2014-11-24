USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewDoctorStaffAssignment(
		IN cpso_number_ VARCHAR(6),
		IN staff_name_ VARCHAR(32)
	)
    BEGIN
    INSERT INTO doctor_staff_assignment_schema 
    (
		cpso_number,
		staff
	)
    VALUES
	(
		cpso_number_,
		staff_name_
	);
END //
DELIMITER ;
