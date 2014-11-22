CREATE TABLE visit_schema(
	patient_id INT NOT NULL,
    cpso_number VARCHAR(6) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    surgery_name VARCHAR(64),
    prescription VARCHAR (256),
    comments VARCHAR(512),
    diagnosis VARCHAR(256),
    PRIMARY KEY (cpso_number, start_time),
    FOREIGN KEY (patient_id) REFERENCES patient_schema(patient_id),
    FOREIGN KEY (cpso_number) REFERENCES doctor_schema (cpso_number),
    FOREIGN KEY (surgery_name) REFERENCES surgery_schema (surgery_name)
);

CREATE TABLE visit_backup_schema(
	patient_id INT NOT NULL,
    cpso_number VARCHAR(6) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    surgery_name VARCHAR(64),
    prescription VARCHAR (256),
    comments VARCHAR(512),
    diagnosis VARCHAR(256),
    inserted_time DATETIME NOT NULL,
    PRIMARY KEY (cpso_number, start_time, inserted_time),
    FOREIGN KEY (patient_id) REFERENCES patient_schema(patient_id),
    FOREIGN KEY (cpso_number) REFERENCES doctor_schema (cpso_number),
    FOREIGN KEY (surgery_name) REFERENCES surgery_schema (surgery_name)
);