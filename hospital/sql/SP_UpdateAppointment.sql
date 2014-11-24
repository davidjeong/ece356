USE ece356;

DELIMITER //
CREATE PROCEDURE UpdateAppointment (
		IN cpso_number_ VARCHAR(6),
        IN start_time_ DATETIME,
        IN new_start_ DATETIME,
        IN new_end_ DATETIME
	)
    BEGIN
    UPDATE visit_schema SET
		start_time = new_start_,
		end_time = new_end_
    WHERE cpso_number = cpso_number_ AND
		start_time = start_time_;
END //
DELIMITER ;
