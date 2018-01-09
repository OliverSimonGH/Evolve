INSERT INTO account_type (id, type) VALUES (1, "Company"), (2, "Customer"), (3, "Assessor");
INSERT INTO people_type (id, type) VALUES (1, "Client"), (2, "Employee"), (3, "Manager");

INSERT INTO account (id, email, password, fk_type) VALUES (1, "company@gmail.com", "123", 1);
INSERT INTO account (id, email, password, fk_type) VALUES (2, "customer@gmail.com", "123", 2);
INSERT INTO account (id, email, password, fk_type) VALUES (3, "assessor@gmail.com", "123", 3);

INSERT INTO moduleType(id, name) VALUES (1, "Clients");
INSERT INTO moduleType(id, name) VALUES (2, "People");
INSERT INTO moduleType(id, name) VALUES (3, "Value");
INSERT INTO moduleType(id, name) VALUES (4, "FM Excellence");
INSERT INTO moduleType(id, name) VALUES (5, "FM Standards");

INSERT INTO assessor(id, name, fk_account) values (1, "bob", 3);
INSERT INTO company(id, name, fk_assessor, fk_account) VALUES (1, "Simon Inc", 1, 1);
INSERT INTO people(id, first_name, last_name, fk_company, fk_account, fk_type) VALUES (1, "Mac", "Tho", 1, 2, 1);

INSERT INTO questionnaire(id, name, fk_module) VALUES (1, "Clients", 1);
INSERT INTO questionnaire(id, name, fk_module) VALUES (2, "People", 2);
INSERT INTO questionnaire(id, name, fk_module) VALUES (3, "Value", 3);
INSERT INTO questionnaire(id, name, fk_module) VALUES (4, "FM Excellence", 4);
INSERT INTO questionnaire(id, name, fk_module) VALUES (5, "FM Standards", 5);

INSERT INTO questions(id, question, fk_questionnaire) VALUES (1, "Accessibility: Availability", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (2, "Accessibility: Easily Contactable", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (3, "Responsiveness: Telephone answered promptly", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (4, "Responsiveness: Prompt response to correspondence", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (5, "Responsiveness: Attending to the issue", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (6, "Reliability: Communicate timescales", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (7, "Reliability: Meet Agreed timescales", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (8, "Reliability: Provision of right service", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (9, "Competence: Demonstrable processes", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (10, "Competence: Demonstrable well trained staff", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (11, "Credibility: General confidence in behaviour and approach", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (12, "Credibility: Honesty", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (13, "Understanding: Demonstrate clear understanding of requirements", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (14, "Understanding: Understanding needs and requirements", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (15, "Understanding: Sympathetic to customer problems and contraints", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (16, "Communication: Recieve regular progress reports", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (17, "Communication: Explain the impact of works", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (18, "Communication: Communicate in an understandable way", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (19, "Communication: Listen to customer", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (20, "Communication: Communicate on-going progress of works/jobs", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (21, "Communication: Communicate job closure", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (22, "Courtesy: Courteous staff", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (23, "Courtesy: Helpful staff", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (24, "Courtesy: Presentable staff", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (25, "Facilities: Hygiene (Cleaning)", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (26, "Facilities: Waste", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (27, "Facilities: Security", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (28, "Facilities: Catering and vending", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (29, "Facilities: Maintenance", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (30, "Facilities: Mailroom", 1);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (31, "Facilities: Help desk", 1);

INSERT INTO questions(id, question, fk_questionnaire) VALUES (32, "I am familiar with the Job Description for the service that I am expected to deliver and my actual roles and responsibilities closely match this", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (33, "A Personal Development Review process (staff appraisal) exists and occurs on a regular basis (12-monthly minimum)", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (34, "My own PDR is fair and reasonable as are the objectives set", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (35, "Training plans exist and these are updated on a regular basis. I am familiar with the TP and I am content with the level of training that I receive.", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (36, "There is strong leadership and management of the service (including company briefings). It works well at all levels", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (37, "I am aware of my Health & Safety and Environmental obligations", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (38, "Clear work instructions and procedures exist and I can refer to them to carry out my work", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (39, "In my opinion, the service(s) being delivered are to a high standard and the client is content with the level of service delivered", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (40, "I receive regular feedback on my performance. Good performance is recognised", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (41, "I am able to feedback my views and comments to the management team and these are considered accordingly", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (42, "Innovation is encouraged from above and I am encouraged to propose innovative ideas. I am incentivised to work efficiently and offer innovative ideas", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (43, "I am given sufficient time to carry out my role and perform my tasks as per my job specification", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (44, "I am motivated, empowered and feel part of the team", 2);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (45, "I receive job satisfaction", 2);

INSERT INTO questions(id, question, fk_questionnaire) VALUES (46, "Do you have a clear FM strategy for the FM services being delivered and how does this strategy ensure value?", 4);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (47, "Are all stakeholders aware of your Service delivery strategy? How is this communicated to all stakeholders?", 4);
INSERT INTO questions(id, question, fk_questionnaire) VALUES (48, "How do you revise your standard policies to meet the requirements of the Client's policies concerning the FM services?", 4);

INSERT INTO assessment(id, date, approved, qvi_score, fk_company) VALUES (1, now(), 0, 89, 1);
INSERT INTO assessment(id, date, approved, qvi_score, fk_company) VALUES (2, now(), 0, 64, 1);
INSERT INTO assessment(id, date, approved, qvi_score, fk_company) VALUES (3, now(), 0, 78, 1);
INSERT INTO assessment(id, date, approved, qvi_score, fk_company) VALUES (4, now(), 0, 42, 1);
INSERT INTO assessment(id, date, approved, qvi_score, fk_company) VALUES (5, now(), 0, 26, 1);





















