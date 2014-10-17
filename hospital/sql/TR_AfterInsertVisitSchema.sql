USE ece356;

DELIMITER //
CREATE TRIGGER AfterInsertVisitSchema
	AFTER INSERT ON visit_schema FOR EACH ROW
    BEGIN
		INSERT INTO visit_backup_schema
		(
			patient_id,
			cpso_number,
			start_time,
			end_time,
			comments,
            inserted_time
		)
		VALUES
        (
			NEW.patient_id,
            NEW.cpso_number,
            NEW.start_time,
            NEW.end_time,
            NEW.comments,
            NOW()
		);
	END
    
    