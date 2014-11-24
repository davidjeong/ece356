USE ece356;

DELIMITER //
CREATE PROCEDURE UpdateVisitInformation(IN prescription_ varchar(256), IN comments_ varchar(512), IN diagnosis_ varchar(256), IN inserted_time_ datetime)
BEGIN
UPDATE visit_schema
SET prescription = prescription_, comments = comments_ , diagnosis = diagnosis_
;

UPDATE visit_backup_schema
SET prescription = prescription_, comments = comments_ , diagnosis = diagnosis_, inserted_time = inserted_time_
;

END //
DELIMITER ;