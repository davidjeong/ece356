USE ece356;

DELIMITER //
CREATE PROCEDURE ViewEarningBySugery(IN surgery_name_ VARCHAR(64))
BEGIN
	SELECT sum(s.cost) as earning_from_sugery_only
	FROM 
		visit_schema v 
	INNER JOIN
		surgery_schema s
	ON
		s.surgery_name = v.surgery_name
	WHERE 
		s.surgery_name = surgery_name_;
END //
DELIMITER ;