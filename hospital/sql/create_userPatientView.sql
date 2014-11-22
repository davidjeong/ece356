CREATE TABLE user_patient_view_schema(
	user_name VARCHAR (32) NOT NULL,
    patient_id INT NOT NULL,
    PRIMARY KEY (user_name, patient_id),
    FOREIGN KEY (user_name) REFERENCES user_schema (user_name),
    FOREIGN KEY (patient_id) REFERENCES patient_schema (patient_id)
);


