INSERT INTO users(username, password, enabled, name, surname, email) VALUES('sergio', '$2a$10$uJn1TfO9UNu2fRVhLsY/POdW1F88ErHypkN2yLAvgzy/GZn.c0ynS', true, 'Sergio', 'Rueskas', 'Rueskas@email.com');
INSERT INTO users(username, password, enabled, name, surname, email) VALUES('admin','$2a$10$mC1sYGU5hCOb98a0sa2B4ulGI.cmUWIYOVUtiToWJyOYnSgbnIDZ2',true, 'Admin', 'Admin', 'Admin@email.com');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users_roles(user_id, role_id) VALUES(1,1);
INSERT INTO users_roles(user_id, role_id) VALUES(2,1);
INSERT INTO users_roles(user_id, role_id) VALUES(2,2);

INSERT INTO regions(name) VALUES('Africa');
INSERT INTO regions(name) VALUES('Asia');
INSERT INTO regions(name) VALUES('Central America');
INSERT INTO regions(name) VALUES('European Union');
INSERT INTO regions(name) VALUES('North America');
INSERT INTO regions(name) VALUES('Oceania');
INSERT INTO regions(name) VALUES('South America');
INSERT INTO regions(name) VALUES('Antarctic');


INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Sergio', 'Rueskas', 'Rueskas@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Belén', 'Rueskas', 'Belen@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (2,'Laura', 'Díaz', 'Diaz@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (3,'Antonio', 'Arroyo', 'Arroyo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (4,'Sergio', 'Requena', 'Requena@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (5,'Silvia', 'Llopis', 'Llopis@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (6,'Marina', 'Crespo', 'Crespo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (7,'Rubén', 'Díaz', 'Ruben@email.com', '2020-04-03');



INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Sergio', 'Rueskas', 'Rueskas@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Belén', 'Rueskas', 'Belen@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (2,'Laura', 'Díaz', 'Diaz@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (3,'Antonio', 'Arroyo', 'Arroyo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (4,'Sergio', 'Requena', 'Requena@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (8,'Silvia', 'Llopis', 'Llopis@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (8,'Marina', 'Crespo', 'Crespo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (8,'Rubén', 'Díaz', 'Ruben@email.com', '2020-04-03');



INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (2,'Sergio', 'Rueskas', 'Rueskas@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (3,'Belén', 'Rueskas', 'Belen@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (3,'Laura', 'Díaz', 'Diaz@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (3,'Antonio', 'Arroyo', 'Arroyo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (4,'Sergio', 'Requena', 'Requena@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Silvia', 'Llopis', 'Llopis@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (1,'Marina', 'Crespo', 'Crespo@email.com', '2020-04-03');
INSERT INTO customers(region_id, name, surname, email, created_at) VALUES (5,'Rubén', 'Díaz', 'Ruben@email.com', '2020-04-03');
