CREATE SCHEMA ece356;
USE ece356;

CREATE TABLE user_schema(
	legal_name VARCHAR(64) NOT NULL,
    user_name VARCHAR(32) NOT NULL,
    user_password VARCHAR(32) NOT NULL,
    user_type ENUM ('doctor', 'patient', 'staff', 'legal', 'finance'),
    PRIMARY KEY (user_name)
);

CREATE TABLE doctor_schema(
	user_name VARCHAR(32) NOT NULL,
    cpso_number VARCHAR(6) NOT NULL,
    department VARCHAR(32),
    PRIMARY KEY (cpso_number),
    FOREIGN KEY (user_name) REFERENCES user_schema(user_name)
);

CREATE TABLE patient_schema(
	user_name VARCHAR(32) NOT NULL,
    default_doctor VARCHAR(6),
    health_status VARCHAR(32),
    health_card_number VARCHAR(32) NOT NULL,
    sin_number VARCHAR(32) NULL,
    phone_number VARCHAR(16),
    address VARCHAR(64),
    patient_id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (patient_id),
    FOREIGN KEY (user_name) REFERENCES user_schema(user_name)
);

CREATE TABLE doctor_staff_assignment_schema(
	cpso_number VARCHAR (6) NOT NULL,
    staff VARCHAR (32) NOT NULL,
    PRIMARY KEY (cpso_number, staff),
    FOREIGN KEY (cpso_number) REFERENCES doctor_schema(cpso_number),
    FOREIGN KEY (staff) REFERENCES user_schema(user_name)
);

CREATE TABLE doctor_patient_view_schema(
	cpso_number VARCHAR (6) NOT NULL,
    patient_id INT NOT NULL,
    PRIMARY KEY (cpso_number, patient_id),
    FOREIGN KEY (cpso_number) REFERENCES doctor_schema (cpso_number),
    FOREIGN KEY (patient_id) REFERENCES patient_schema (patient_id)
);

CREATE TABLE surgery_schema(
	surgery_name VARCHAR(64),
    cost NUMERIC(15,2) NOT NULL,
    PRIMARY KEY (surgery_name)
);

CREATE TABLE visit_schema(
	patient_id INT NOT NULL,
    cpso_number VARCHAR(6) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    surgery_name VARCHAR(64),
    prescription VARCHAR (256),
    comments VARCHAR(512),
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
    inserted_time DATETIME NOT NULL,
    PRIMARY KEY (cpso_number, start_time, inserted_time),
    FOREIGN KEY (patient_id) REFERENCES patient_schema(patient_id),
    FOREIGN KEY (cpso_number) REFERENCES doctor_schema (cpso_number),
    FOREIGN KEY (surgery_name) REFERENCES surgery_schema (surgery_name)
);