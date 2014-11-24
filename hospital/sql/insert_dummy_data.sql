USE ece356;

INSERT INTO user_schema (legal_name, user_name, user_password, user_type) VALUES
('Generic Doctor', 'doctor', 'doctor', 'doctor'),
('Allycia Pheonix', 'aphoen423', 'dsjfie', 'doctor'),
('Agatha Wallis', 'awallis1', 'fijcxvlQ', 'doctor'),
('Gerald Lizette', 'glizet94', 'Jjidjfs', 'doctor'),
('Molly Jack', 'mjack45', 'lkdsjf', 'doctor'),
('Albert Flores', 'aflores02', 'vnkxn', 'doctor'),

('Generic Patient', 'patient', 'patient', 'patient'),
('Marlene Simon', 'msimon1', 'jijx', 'patient'),
('Gordon Casey','gcasey','jixvxzse','patient'),
('Opal Sutton','osutton813','jijixcv','patient'),
('Irving Ryan', 'iryan2','ijlijhvcx','patient'),
('Doyle Walker','dwalker', 'ivhoixh', 'patient'),
('Jeremy Fields', 'jfields123', 'lkjlijx', 'patient'),
('Rebecca Lucas','rlucas','rlucas1','patient'),
('Marie Wilkerson','mwilkerson','mwilkerson1','patient'),
('Arnold Phelps','aphelps','aphelps1','patient'),
('Santos Dawson','sdawson', 'ijuse', 'patient'),
('Teresa Munoz','tmunoz','ijljsd','patient'),
('Jamie Gibbs','jgibbs','qiybztr','patient'),
('Jeremiah Peters','jpeters','sdfscxv','patient'),
('Gina Lloyd','glloyd','glloyd1','patient'),
('David Hamilton','dhamilton','dhamilton1','patient'),
('Rosie Myers','rmyers','rmyers1','patient'),

('Generic Staff', 'staff', 'staff', 'staff'),
('Maggie Byrd','mbyrd','mbyrd1','staff'),
('Casey Robinson','crobinson','cRobinson1','staff'),
('Christopher Reed','creed','creed1','staff'),

('Generic Legal', 'legal', 'legal', 'legal'),
('James Bond','jbond','doubleoh','legal'),
('Rick James','rjames','qweasdzxc','legal'),

('Generic Finance', 'finance', 'finance', 'finance'),
('Leroy Jenkins','ljins','sdfewr','finance'),
('Blanca Carr', 'bcarr23', 'backses','finance');

INSERT INTO doctor_schema (user_name, cpso_number, department) VALUES
('doctor', '000000', 'Family'),
('aphoen423', '287371', 'Dental'),
('awallis1', '432821', 'Cardiology'),
('glizet94', '494882', 'Neurology'),
('mjack45', '018375', 'ENT'),
('aflores02', '283841', 'Physiotherapy');

INSERT INTO patient_schema (user_name, default_doctor, health_status, health_card_number, sin_number, phone_number, address)  VALUES
('patient', '000000', 'healthy', '392817364932', '2123', '4163921219', null),
('msimon1', '000000', 'chronic sickness', '203948574832', '2844', '4163242331', null),
('gcasey', '000000', 'healthy with mild diet issues', '394857481022', '8493', '4168392810', null),
('osutton813', '000000', 'healthy', '332221233211', '8491', '4164839392', null),
('iryan2', '432821', 'unhealthy', '928309223832', '7446', '4164839984', null),
('dwalker', '018375', 'chronic issues with ear', '383726182112', '4764', '4163221121', null),
('jfields123', '494882', 'recent stroke', '232183048321', '4893', '4163221811', null),
('rlucas', '287371', 'dentures', '494088764333', '9843', '4160388401', null),
('mwilkerson', '018375', 'chronic nosebleeds', '329873212321', '6732', '4160883201', null),
('aphelps', '000000', 'need regular checkups', '112839485744', '7321', null, null),
('sdawson', '283841', 'recent amputation', '876493871231', '1231', null, null),
('tmunoz', '283841', 'back injury', '348403823121', '8794', null, null),
('jgibbs', '432821', 'recent heart attack', '947363819273', '3878', null, null),
('jpeters', '000000', 'healthy', '483038472612', '8932', null, null),
('glloyd', '287371', 'braces', '987463829121', '2832', null, null),
('dhamilton', '431821', 'low blood pressure', '382917263826', '7362', null, null),
('rmyers', '432821', 'high blood pressure', '382917291732', '2342', null, null);

INSERT INTO doctor_staff_assignment_schema (cpso_number, staff) VALUES
('283841', 'staff'),
('287371', 'staff'),
('432821', 'staff'),
('000000', 'mbyrd'),
('494882', 'crobinson'),
('018375', 'creed');

INSERT INTO surgery_schema (surgery_name, cost) VALUES
('Dental Cleaning', '120'),
('Simple Checkup', '50'),
('Organ Transplant', '45000'),
('Broken Bone', '100'),
('Neurosurgery', '53000'),
('Osteotomy', '7000'),
('Amputation', '5000'),
('Escharotomy', '2300'),
('Biopsy', '500'),
('X-ray Scan', '130'),
('MRI Scan', '400'),
('Endoscopy', '250');


INSERT INTO user_patient_view_schema (user_name, patient_id) VALUES
('patient','1'),
('doctor','1'),
('doctor', '2'),
('doctor', '3'),
('doctor', '4'),
('mbyrd', '2'),
('mbyrd', '3'),
('mbyrd', '4'),
('awallis1', '2'),
('staff', '2'),
('staff', '3'),
('aflores02', '3');


