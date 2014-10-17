USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientRecords(IN health_card_number_ VARCHAR(32))
	BEGIN
    SELECT 
		v.start_time,
        v.end_time,
        v.comments,
        v.surgery_name,
		v.prescription
	FROM
		visit_schema v
	WHERE
		v.health_card_number = health_card_number_;
	END //
DELIMITER ;