USE ece356;

DELIMITER //
CREATE TRIGGER AfterUpdateVisitSchema
	AFTER UPDATE ON visit_schema FOR EACH ROW
    BEGIN
		INSERT INTO visit_backup_schema
		(
			health_card_number,
			cpso_number,
			start_time,
			end_time,
            surgery_name,
            prescription,
			comments,
            inserted_time
		)
		VALUES
        (
			NEW.health_card_number,
            NEW.cpso_number,
            NEW.start_time,
            NEW.end_time,
            NEW.surgery_name,
            NEW.prescription,
            NEW.comments,
            NOW()
		);
	END
    
    