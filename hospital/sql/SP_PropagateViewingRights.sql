USE ece356;

DELIMITER //
CREATE PROCEDURE PropagateViewingRights(IN cpso_number_ varchar(6), IN patient_id_ int(11))
BEGIN

    SET @doctor_username := (SELECT user_name FROM doctor_schema WHERE cpso_number = cpso_number_);
	
    DELETE FROM 
		user_patient_view_schema
	WHERE 
		user_name = @doctor_username AND patient_id = patient_id_;
    
	DELETE FROM 
		user_patient_view_schema
	WHERE
		patient_id = _patient_id
	AND user_name IN
		(
        SELECT staff FROM
			doctor_staff_assignment_schema
		WHERE cpso_number = cpso_number_
	);
	COMMIT;
    
END //