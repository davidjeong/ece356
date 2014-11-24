
use ece356;

DELIMITER //
CREATE PROCEDURE CountRevenueFromAllSurgeries( IN start_time_ datetime, IN end_time_ datetime)
BEGIN
	SELECT count(*) as visits, sum(s.cost) as cost
	FROM 
		visit_schema v 
	INNER JOIN
		surgery_schema s
	ON
		v.surgery_name = s.surgery_name
	WHERE 
		v.surgery_name != 'None' AND start_time_ <= v.start_time AND v.end_time <= end_time_;
END //
DELIMITER ;