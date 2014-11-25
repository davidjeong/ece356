USE ece356;

DELIMITER //
CREATE PROCEDURE GetDoctorUsername (
        IN cpso_number_ VARCHAR(6)
	)
    
    BEGIN
    
   SELECT d.user_name
   FROM doctor_schema d
   WHERE d.cpso_number = cpso_number_;
	
END //
DELIMITER ;