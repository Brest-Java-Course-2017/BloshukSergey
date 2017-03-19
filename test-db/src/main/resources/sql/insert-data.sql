INSERT INTO session (session_id, movie_name, session_date, total_seats) VALUES ('1', 'Logan', '2017-03-3', '60');
INSERT INTO session (session_id, movie_name, session_date, total_seats) VALUES ('2', 'Moonlight', '2017-04-3', '80');
INSERT INTO session (session_id, movie_name, session_date, total_seats) VALUES ('3', 'Spotlight', '2017-05-3', '20');
INSERT INTO session (session_id, movie_name, session_date, total_seats) VALUES ('4', 'Argo', '2017-06-3', '50');
INSERT INTO session (session_id, movie_name, session_date, total_seats) VALUES ('5', 'Gladiator', '2017-07-3', '60');

INSERT INTO customer (customer_id, name) VALUES ('1', 'Sergey Bloshuk');
INSERT INTO customer (customer_id, name) VALUES ('2', 'Vadim Parafenyuk');
INSERT INTO customer (customer_id, name) VALUES ('3', 'Egor Pavlyuchuk');
INSERT INTO customer (customer_id, name) VALUES ('4', 'Denis Nalivko');
INSERT INTO customer (customer_id, name) VALUES ('5', 'Sergey Peshko');
INSERT INTO customer (customer_id, name) VALUES ('6', 'Mikhail Nazarevich');

INSERT INTO booking (session_id, customer_id) VALUES ('1', '1');
INSERT INTO booking (session_id, customer_id) VALUES ('1', '2');
INSERT INTO booking (session_id, customer_id) VALUES ('1', '3');
INSERT INTO booking (session_id, customer_id) VALUES ('1', '2');
INSERT INTO booking (session_id, customer_id) VALUES ('2', '3');
INSERT INTO booking (session_id, customer_id) VALUES ('2', '4');
INSERT INTO booking (session_id, customer_id) VALUES ('5', '5');
INSERT INTO booking (session_id, customer_id) VALUES ('4', '6');



