USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewVisitRecord (
		IN patient_id_ INT,
		IN cpso_number_ VARCHAR(6),
        IN start_time_ DATETIME,
        IN end_time_ DATETIME,
        IN comments_ VARCHAR(128)
	)
    BEGIN
    INSERT INTO visit_schema 
    (
		patient_id,
        cpso_number,
        start_time,
        end_time,
        comments
	)
    VALUES
	(
        patient_id_,
        cpso_number_,
        start_time_,
        end_time_,
        comments_
	);
END //
DELIMITER ;


