USE ece356;

DELIMITER //
CREATE PROCEDURE ViewPatientInformation(IN patient_id_ INT)
	BEGIN
    SELECT 
		*
	FROM
		patient_schema p
	WHERE
		p.patient_id = patient_id_;
	END //
DELIMITER ;