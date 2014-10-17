USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewVisitRecord (
		IN health_card_number_ VARCHAR(32),
		IN cpso_number_ VARCHAR(6),
        IN start_time_ DATETIME,
        IN end_time_ DATETIME,
        IN comments_ VARCHAR(128)
	)
    BEGIN
    INSERT INTO visit_schema 
    (
		health_card_number,
        cpso_number,
        start_time,
        end_time,
        comments
	)
    VALUES
	(
        health_card_number_,
        cpso_number_,
        start_time_,
        end_time_,
        comments_
	);
END //
DELIMITER ;


