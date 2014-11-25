USE ece356;

DELIMITER //
CREATE PROCEDURE StaffViewPatientInformation()
    BEGIN
		select p.default_doctor, p.health_status, p.health_card_number, p.phone_number, p.address, up.patient_id
		from doctor_schema d
		INNER JOIN user_patient_view_schema up on up.user_name = d.user_name
		INNER JOIN patient_schema p on up.patient_id = p.patient_id
        Order by up.patient_id;
	END //
DELIMITER ;