USE ece356;

DELIMITER //
CREATE PROCEDURE DeleteAllDoctorStaffAssignmentsForDoctor(IN cpso_number_ VARCHAR(32))
    BEGIN
		DELETE ds
		FROM doctor_staff_assignment_schema ds
		WHERE ds.cpso_number = cpso_number_;
	END //
DELIMITER ;
