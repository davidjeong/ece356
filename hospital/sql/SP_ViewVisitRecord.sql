USE ece356;

DELIMITER //
CREATE PROCEDURE ViewVisitRecord(IN username varchar(32))
	BEGIN
    SELECT
		*
    FROM	
		visit_schema v
	INNER JOIN
		user_patient_view_schema up
        ON up.patient_id = v.patient_id
	WHERE 
		up.user_name = username
	ORDER BY v.start_time DESC;
	END //
DELIMITER ;
