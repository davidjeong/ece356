USE ece356;

DELIMITER //
CREATE TRIGGER AfterInsertVisitSchema
	AFTER INSERT ON visit_schema FOR EACH ROW
    BEGIN
		INSERT INTO visit_backup_schema
		(
			health_card_number,
			cpso_number,
			start_time,
			end_time,
			comments,
            inserted_time
		)
		VALUES
        (
			NEW.health_card_number,
            NEW.cpso_number,
            NEW.start_time,
            NEW.end_time,
            NEW.comments,
            NOW()
		);
	END
    
    