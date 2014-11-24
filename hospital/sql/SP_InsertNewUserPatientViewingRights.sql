USE ece356;

DELIMITER //
CREATE PROCEDURE InsertNewUserPatientViewingRights(
		IN user_name_ VARCHAR(32),
		IN patient_id_ INT
	)
    BEGIN
    INSERT INTO user_patient_view_schema 
    (
		user_name,
		patient_id
	)
    VALUES
	(
		user_name_,
		patient_id_
	);
END //
DELIMITER ;
