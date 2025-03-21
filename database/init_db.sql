CREATE SCHEMA IF NOT EXISTS "testmaster";

CREATE TABLE "user" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "first_name" VARCHAR(50) NOT NULL,
    "last_name" VARCHAR(50) NOT NULL,
    "phone_number" VARCHAR(15) NOT NULL,
);

CREATE TABLE "account" (
    "user_id" UUID PRIMARY KEY,
    "email" VARCHAR(255) NOT NULL,
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

CREATE TABLE "class" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(50) NOT NULL,
);

CREATE TABLE "exam" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "title" VARCHAR(50) NOT NULL,
    "description" VARCHAR(255),
    "passcode" VARCHAR(15),
    "time_limit" INT,
    "scheduled_date" TIMESTAMP NOT NULL    
);