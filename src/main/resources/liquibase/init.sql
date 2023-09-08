-- liquibase formatted sql

-- changeset Vladimir:1

CREATE TABLE "users"
(
    "user_id"        BIGSERIAL    NOT NULL PRIMARY KEY,
    "name"           VARCHAR(255) NOT NULL,
    "age"            INTEGER      NOT NULL,
    "password"       VARCHAR(255) NOT NULL,
    "role"           VARCHAR(255),
    "deleted_status" bool default false
);



CREATE TABLE "house"
(
    "house_id"       BIGSERIAL PRIMARY KEY,
    "address"        VARCHAR(255),
    "deleted_status" bool default false,
    "owner_id"       BIGSERIAL REFERENCES "users" ("user_id"),
    FOREIGN KEY ("owner_id") REFERENCES "users" ("user_id")
);

CREATE TABLE "house_tenants"
(
    "house_id"  BIGSERIAL NOT NULL,
    "tenant_id" BIGSERIAL NOT NULL,
    PRIMARY KEY ("house_id", "tenant_id"),
    FOREIGN KEY ("house_id") REFERENCES "house" ("house_id"),
    FOREIGN KEY ("tenant_id") REFERENCES "users" ("user_id")
);