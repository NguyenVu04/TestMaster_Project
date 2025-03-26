CREATE SCHEMA IF NOT EXISTS "public";

CREATE TABLE "user" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "first_name" VARCHAR(50) NOT NULL,
    "last_name" VARCHAR(50) NOT NULL,
    "phone_number" VARCHAR(15) NOT NULL
);

CREATE TABLE "account" (
    "user_id" UUID PRIMARY KEY,
    "email" VARCHAR(255) UNIQUE NOT NULL,
    "password" VARCHAR(255) NOT NULL
);

CREATE TABLE "student" (
    "user_id" UUID PRIMARY KEY
);

CREATE TABLE "teacher" (
    "user_id" UUID PRIMARY KEY
);

CREATE TABLE "admin" (
    "user_id" UUID PRIMARY KEY
);

CREATE TABLE "exam" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "teacher_id" UUID,
    "title" VARCHAR(50) NOT NULL,
    "description" VARCHAR(255),
    "attempt_limit" SMALLINT,
    "passcode" VARCHAR(15),
    "start_time" TIMESTAMP NOT NULL,
    "end_time" TIMESTAMP NOT NULL,
    "time_limit" INT
);

CREATE TYPE question_type AS ENUM ('MULTIPLE_CHOICE', 'SHORT_ANSWER');

CREATE TABLE "question" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "type" question_type NOT NULL,
    "content" TEXT NOT NULL,
    "media_url" TEXT[],
    "options" TEXT[],
    "answer" TEXT
);

CREATE TABLE "notification" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "sender_id" UUID NOT NULL,
    "receiver_id" UUID NOT NULL,
    "title" VARCHAR(50) NOT NULL,
    "content" TEXT NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "seen" BOOLEAN NOT NULL
);

CREATE TABLE "exam_question" (
    "exam_id" UUID NOT NULL,
    "question_id" UUID NOT NULL,
    "number" INT NOT NULL,
    "score" REAL NOT NULL,
    "auto_score" BOOLEAN NOT NULL,
    PRIMARY KEY ("exam_id", "question_id")
);

CREATE TABLE "exam_student" (
    "attempt_id" SMALLSERIAL NOT NULL,
    "exam_id" UUID NOT NULL,
    "student_id" UUID NOT NULL,
    "total_score" REAL,
    "feedback" TEXT,
    "start_time" TIMESTAMP NOT NULL,
    "end_time" TIMESTAMP NOT NULL,
    "submitted" BOOLEAN NOT NULL,
    PRIMARY KEY ("attempt_id", "exam_id", "student_id")
);

CREATE TABLE "exam_student_answer" (
    "attempt_id" SMALLSERIAL NOT NULL,
    "exam_id" UUID NOT NULL,
    "student_id" UUID NOT NULL,
    "question_id" UUID NOT NULL,
    "answer" TEXT,
    "score" REAL,
    PRIMARY KEY ("attempt_id", "exam_id", "student_id", "question_id")
);

CREATE TABLE "otp" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "otp" VARCHAR(6) NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "expired_at" TIMESTAMP NOT NULL
);

ALTER TABLE "account" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE;
ALTER TABLE "student" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE;
ALTER TABLE "teacher" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE;
ALTER TABLE "admin" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE;
ALTER TABLE "exam" 
    ADD FOREIGN KEY ("teacher_id") REFERENCES "teacher" ("user_id") ON DELETE SET NULL;
ALTER TABLE "notification" 
    ADD FOREIGN KEY ("sender_id") REFERENCES "user" ("id") ON DELETE CASCADE,
    ADD FOREIGN KEY ("receiver_id") REFERENCES "user" ("id") ON DELETE CASCADE;
ALTER TABLE "exam_question"
    ADD FOREIGN KEY ("exam_id") REFERENCES "exam" ("id") ON DELETE CASCADE,
    ADD FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON DELETE CASCADE;
ALTER TABLE "exam_student"
    ADD FOREIGN KEY ("exam_id") REFERENCES "exam" ("id") ON DELETE CASCADE,
    ADD FOREIGN KEY ("student_id") REFERENCES "student" ("user_id") ON DELETE CASCADE;
ALTER TABLE "exam_student_answer"
    ADD FOREIGN KEY ("exam_id") REFERENCES "exam" ("id") ON DELETE CASCADE,
    ADD FOREIGN KEY ("student_id") REFERENCES "student" ("user_id") ON DELETE CASCADE,
    ADD FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON DELETE CASCADE;
ALTER TABLE "otp" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id") ON DELETE CASCADE;