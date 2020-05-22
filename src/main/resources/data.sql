INSERT INTO users (id,username, email, PASSWORD) VALUES
  (1, 'Greta Thunberg', 'greta.thunberg@gmail.com', 'greta'),
  (2, 'Valtteri Bottas', 'valera@f1.com', 'bottas'),
  (3, 'Oleg Vynnyk', 'olegvynnyk@gmail.com', 'olegvynnyk');

INSERT INTO animals (id , name, breed, gender, age, arrival_date) VALUES
  (1, 'Rita', 'cat', 'female', 14, '2005-09-12'),
  (2, 'Panas', 'fish', 'male', 9, '2011-09-05'),
  (3, 'Erzhan', 'monkey', 'male', 1, '2019-08-14'),
  (4, 'Karel', 'cat', 'male', 2, '2018-09-01');


INSERT INTO requests (id, animal_id, user_id, date) VALUES
  (0, 1, 1, '2020-05-20 14:18:50'),
  (1, 3, 2, '2020-05-05 11:59:00');

INSERT INTO user_roles(id, name) VALUES
(1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users_roles(users_id, roles_id) VALUES
(1, 1),
(2, 1),
(3, 2);