USE ece356;

DELIMITER //
CREATE PROCEDURE PropagateViewingRights(IN cpso_number_ varchar(6), IN patient_id_ int(11))
BEGIN

    SET @doctor_username := (SELECT user_name FROM doctor_schema WHERE cpso_number = cpso_number_);
	
    DELETE FROM 
		user_patient_view_schema
	WHERE 
		user_name = @doctor_username AND patient_id = patient_id_;
    
    CREATE TEMPORARY TABLE IF NOT EXISTS tmpTable 
    AS (SELECT DISTINCT ds.staff
        FROM
			user_patient_view_schema u
		INNER JOIN 
			doctor_schema d
		ON
			d.user_name = u.user_name
		INNER JOIN
			doctor_staff_assignment_schema ds
		ON
			ds.cpso_number = d.cpso_number
		WHERE ds.cpso_number in
			(
				select dd.cpso_number
                from doctor_schema dd
                inner join user_patient_view_schema uu
                where dd.user_name = uu.user_name and uu.patient_id = patient_id
	));
    
    
	DELETE FROM 
		user_patient_view_schema
	WHERE
    patient_id = patient_id and
	user_name not in
		(
        SELECT staff FROM tmpTable
        UNION
        SELECT d.user_name
        From doctor_schema d
	);
	COMMIT;
    
END //