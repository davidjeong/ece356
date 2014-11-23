USE ece356;

DELIMITER //
CREATE PROCEDURE ViewEarningByTime(IN start_time_ DATETIME, IN end_time_ DATETIME)
BEGIN
	SELECT sum(s.cost + v.standard_cost) as earnings_given_time
	FROM 
		visit_schema v 
	INNER JOIN
		surgery_schema s
	ON
		s.surgery_name = v.surgery_name
	WHERE 
		v.start_time>= start_time_ and v.end_time<= end_time_;
END //
DELIMITER ;