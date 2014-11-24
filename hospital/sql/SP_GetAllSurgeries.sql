USE ece356;

DELIMITER //
CREATE PROCEDURE GetAllSurgeries()
BEGIN
	SELECT surgery_name
	FROM surgery_schema
	ORDER BY surgery_name;
END //
DELIMITER ;

call GetAllSurgeries();