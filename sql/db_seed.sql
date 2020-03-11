/* 
Seed script that contains dummy data for use with the Library project.
*/

-- Roles -- 
INSERT INTO role (name)
values ('ROLE_ADMIN');
INSERT INTO role (name)
values ('ROLE_USER');

-- Library Accounts --
INSERT INTO library_account (first_name, last_name, nuid, pin, role_id)
VALUES ('Karl', 'Admin', '11111111', '$2y$12$I8TwC.DhCVrfWYKQPR/wT.nXJ7TrilsLuNoVzqTU0HgM19C4Ro5hS',
        (SELECT id FROM role where role.name = 'ROLE_ADMIN'));
INSERT INTO library_account (first_name, last_name, nuid, pin, role_id)
VALUES ('Matt', 'Student', '22222222', '$2y$12$H.OacR5VLhOuZN.z1grq7u3Fgb4viKCjAx5kJw6TgnMZMQfZvGRkS',
        (SELECT id FROM role where role.name = 'ROLE_USER'));