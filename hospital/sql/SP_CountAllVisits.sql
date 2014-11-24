USE ece356;

DELIMITER //
CREATE PROCEDURE CountAllVisits(IN start_time_ datetime, IN end_time_ datetime)
BEGIN
	SELECT count(*) as visits, sum(v.standard_cost) as cost
	FROM 
		visit_schema v 
	WHERE 
		start_time_ <= v.start_time AND v.end_time <= end_time_;
END //
DELIMITER ;
