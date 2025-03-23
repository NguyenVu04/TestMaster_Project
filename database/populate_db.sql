-- Insert data into "user" table
INSERT INTO "user" ("id", "first_name", "last_name", "phone_number") VALUES
('11111111-1111-1111-1111-111111111111', 'John', 'Doe', '1234567890'),
('22222222-2222-2222-2222-222222222222', 'Jane', 'Smith', '0987654321'),
('33333333-3333-3333-3333-333333333333', 'Alice', 'Johnson', '5555555555'),
('44444444-4444-4444-4444-444444444444', 'Bob', 'Brown', '4444444444'),
('55555555-5555-5555-5555-555555555555', 'Charlie', 'Davis', '3333333333'),
('66666666-6666-6666-6666-666666666666', 'David', 'Wilson', '2222222222'),
('77777777-7777-7777-7777-777777777777', 'Eva', 'Martinez', '1111111111'),
('88888888-8888-8888-8888-888888888888', 'Frank', 'Garcia', '6666666666'),
('99999999-9999-9999-9999-999999999999', 'Grace', 'Lee', '7777777777'),
('00000000-0000-0000-0000-000000000000', 'Hank', 'Kim', '8888888888'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Ivy', 'Clark', '9999999999'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Jack', 'Lewis', '1010101010'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Karen', 'Walker', '1212121212'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Leo', 'Hall', '1313131313'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Mia', 'Allen', '1414141414');

-- Insert data into "account" table
INSERT INTO "account" ("user_id", "email", "password") VALUES
('11111111-1111-1111-1111-111111111111', 'john.doe@example.com', 'admin'),
('22222222-2222-2222-2222-222222222222', 'jane.smith@example.com', 'admin'),
('33333333-3333-3333-3333-333333333333', 'alice.johnson@example.com', 'admin'),
('44444444-4444-4444-4444-444444444444', 'bob.brown@example.com', 'admin'),
('55555555-5555-5555-5555-555555555555', 'charlie.davis@example.com', 'admin'),
('66666666-6666-6666-6666-666666666666', 'david.wilson@example.com', 'admin'),
('77777777-7777-7777-7777-777777777777', 'eva.martinez@example.com', 'admin'),
('88888888-8888-8888-8888-888888888888', 'frank.garcia@example.com', 'admin'),
('99999999-9999-9999-9999-999999999999', 'grace.lee@example.com', 'admin'),
('00000000-0000-0000-0000-000000000000', 'hank.kim@example.com', 'admin'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ivy.clark@example.com', 'admin'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'jack.lewis@example.com', 'admin'),
('cccccccc-cccc-cccc-cccc-cccccccccccc', 'karen.walker@example.com', 'admin'),
('dddddddd-dddd-dddd-dddd-dddddddddddd', 'leo.hall@example.com', 'admin'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'mia.allen@example.com', 'admin');

-- Insert data into "student" table
INSERT INTO "student" ("user_id") VALUES
('11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222222'),
('33333333-3333-3333-3333-333333333333'),
('44444444-4444-4444-4444-444444444444'),
('55555555-5555-5555-5555-555555555555');

-- Insert data into "teacher" table
INSERT INTO "teacher" ("user_id") VALUES
('66666666-6666-6666-6666-666666666666'),
('77777777-7777-7777-7777-777777777777'),
('88888888-8888-8888-8888-888888888888'),
('99999999-9999-9999-9999-999999999999'),
('00000000-0000-0000-0000-000000000000');

-- Insert data into "admin" table
INSERT INTO "admin" ("user_id") VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
('cccccccc-cccc-cccc-cccc-cccccccccccc'),
('dddddddd-dddd-dddd-dddd-dddddddddddd'),
('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee');

-- Insert data into "exam" table
INSERT INTO "exam" ("id", "teacher_id", "title", "description", "attempt_limit", "passcode", "start_time", "end_time", "time_limit") VALUES
('11111111-1111-1111-1111-111111111111', '66666666-6666-6666-6666-666666666666', 'Math Exam 1', 'First Math Exam', 3, 'MATH123', '2025-03-21 09:00:00', '2025-03-21 11:00:00', 120),
('22222222-2222-2222-2222-222222222222', '77777777-7777-7777-7777-777777777777', 'Science Exam 1', 'First Science Exam', 3, 'SCI123', '2025-03-22 09:00:00', '2025-03-22 11:00:00', 120),
('33333333-3333-3333-3333-333333333333', '88888888-8888-8888-8888-888888888888', 'History Exam 1', 'First History Exam', 3, 'HIST123', '2025-03-23 09:00:00', '2025-03-23 11:00:00', 120),
('44444444-4444-4444-4444-444444444444', '99999999-9999-9999-9999-999999999999', 'English Exam 1', 'First English Exam', 3, 'ENG123', '2025-03-24 09:00:00', '2025-03-24 11:00:00', 120),
('55555555-5555-5555-5555-555555555555', '00000000-0000-0000-0000-000000000000', 'Art Exam 1', 'First Art Exam', 3, 'ART123', '2025-03-25 09:00:00', '2025-03-25 11:00:00', 120);

-- Insert data into "question" table
INSERT INTO "question" ("id", "type", "content", "media_url", "options", "answer") VALUES
('11111111-1111-1111-1111-111111111111', 'MULTIPLE_CHOICE', 'What is 2+2?', '{}', '{"2", "3", "4", "5"}', '4'),
('22222222-2222-2222-2222-222222222222', 'SHORT_ANSWER', 'Explain the theory of relativity.', '{}', '{}', 'It is a theory by Einstein.'),
('33333333-3333-3333-3333-333333333333', 'MULTIPLE_CHOICE', 'What is the capital of France?', '{}', '{"Berlin", "Madrid", "Paris", "Rome"}', 'Paris'),
('44444444-4444-4444-4444-444444444444', 'SHORT_ANSWER', 'Describe the process of photosynthesis.', '{}', '{}', 'It is the process by which plants make food.'),
('55555555-5555-5555-5555-555555555555', 'MULTIPLE_CHOICE', 'Which planet is known as the Red Planet?', '{}', '{"Earth", "Mars", "Jupiter", "Saturn"}', 'Mars');

-- Insert data into "notification" table
INSERT INTO "notification" ("id", "sender_id", "receiver_id", "title", "content", "created_at", "seen") VALUES
('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', 'Welcome', 'Welcome to the system!', '2025-03-21 08:00:00', false),
('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', '33333333-3333-3333-3333-333333333333', 'Exam Reminder', 'Dont forget about the exam tomorrow.', '2025-03-21 09:00:00', false),
('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', '44444444-4444-4444-4444-444444444444', 'Assignment Due', 'Your assignment is due next week.', '2025-03-21 10:00:00', false),
('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', '55555555-5555-5555-5555-555555555555', 'Meeting Scheduled', 'A meeting has been scheduled for next Monday.', '2025-03-21 11:00:00', false),
('55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111', 'System Update', 'The system will be updated tonight.', '2025-03-21 12:00:00', false);

-- Insert data into "exam_question" table
INSERT INTO "exam_question" ("exam_id", "question_id") VALUES
('11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'),
('22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222'),
('33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333'),
('44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444'),
('55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555');

-- Insert data into "exam_student" table
INSERT INTO "exam_student" ("attempt_id", "exam_id", "student_id", "score", "feedback", "start_time", "end_time") VALUES
(1, '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 95.0, 'Great job!', '2025-03-21 09:00:00', '2025-03-21 10:00:00'),
(2, '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 88.0, 'Well done!', '2025-03-22 09:00:00', '2025-03-22 10:00:00'),
(3, '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 92.0, 'Excellent!', '2025-03-23 09:00:00', '2025-03-23 10:00:00'),
(4, '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 85.0, 'Good effort!', '2025-03-24 09:00:00', '2025-03-24 10:00:00'),
(5, '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 90.0, 'Nice work!', '2025-03-25 09:00:00', '2025-03-25 10:00:00');

-- Insert data into "exam_student_answer" table
INSERT INTO "exam_student_answer" ("attempt_id", "exam_id", "student_id", "question_id", "answer") VALUES
(1, '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', '4'),
(2, '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'It is a theory by Einstein.'),
(3, '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'Paris'),
(4, '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444', 'It is the process by which plants make food.'),
(5, '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', '55555555-5555-5555-5555-555555555555', 'Mars');