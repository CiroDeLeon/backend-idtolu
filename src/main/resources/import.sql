-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');
INSERT INTO users (id,username, password,role) VALUES (100099,'johndoe10', '$2a$10$zJB3jRb6JvmEp60OLxCmZu0OuF9MV9gqvz5vNuRSNeihMbWc04YzW','ADMIN');