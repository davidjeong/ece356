USE ece356;

DELIMITER //
CREATE PROCEDURE ViewAllVisitRecords(IN username varchar(32))
	BEGIN
    SELECT
		*
    FROM	
		visit_schema v
	ORDER BY v.start_time DESC;
	END //
DELIMITER ;
