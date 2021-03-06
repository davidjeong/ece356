USE ece356;

DELIMITER //
CREATE PROCEDURE ViewEarningByDoctor(IN cpso_number_ VARCHAR (6), IN start_time_ DATETIME, IN end_time_ DATETIME)
BEGIN
	SELECT sum(s.cost) + sum(v.standard_cost) as earning
	FROM 
		visit_schema v 
	LEFT JOIN
		surgery_schema s
	ON
		s.surgery_name = v.surgery_name
	WHERE 
		v.cpso_number = cpso_number_ and v.start_time>=start_time_ and v.end_time <=end_time_;
END //
DELIMITER ;