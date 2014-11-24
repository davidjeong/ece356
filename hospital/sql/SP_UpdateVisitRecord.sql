USE ece356;

DELIMITER //
CREATE PROCEDURE UpdateVisitRecord (
		IN patient_id_ INT,
		IN cpso_number_ VARCHAR(6),
        IN start_time_ DATETIME,
        IN prescription_ VARCHAR(256),
        IN comments_ VARCHAR(512),
        IN diagnosis_ VARCHAR(256)
	)
    BEGIN
    UPDATE visit_schema SET 
        prescription = prescription_,
        comments = comments_,
        diagnosis = diagnosis_
	WHERE
		patient_id = patient_id_ AND
        cpso_number = cpso_number_ AND
        start_time = start_time_;
END //
DELIMITER ;


