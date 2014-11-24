USE ece356;

DELIMITER //
CREATE PROCEDURE DeleteVisitRecord (
		IN cpso_number_ VARCHAR(6),
        IN start_time_ DATETIME
	)
    BEGIN
    DELETE FROM visit_schema 
    WHERE cpso_number = cpso_number_
    AND start_time = start_time_;
END //
DELIMITER ;