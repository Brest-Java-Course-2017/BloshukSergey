INSERT INTO session (session_id, movie_name, session_date) VALUES (1,'Logan',                       '2017-03-3');
INSERT INTO session (session_id, movie_name, session_date) VALUES (2,'Pirates of the Caribbean',    '2017-10-8');
INSERT INTO session (session_id, movie_name, session_date) VALUES (3,'Moonlight',                   '2017-4-8');
INSERT INTO session (session_id, movie_name, session_date) VALUES (4,'Spotlight',                   '2017-5-8');
INSERT INTO session (session_id, movie_name, session_date) VALUES (5,'Argo',                        '2017-6-8');
INSERT INTO session (session_id, movie_name, session_date) VALUES (6,'Gladiator',                   '2017-7-8');

INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (1, 1, 'Sergey',     'Bloshuk',      2);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (2, 6, 'Vadim',      'Parafenyuk',   3);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (3, 1, 'Sergey',     'Bloshuk',      1);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (4, 2, 'Egor',       'Pavlyuchuk',   2);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (5, 1, 'Mikhail',    'Nazarevich',   4);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (6, 3, 'Sergey',     'Peshko',       5);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (7, 2, 'Dima',       'Bratchenya',   1);
INSERT INTO customer (customer_id, session_id, first_name, last_name, quantity_tickets) VALUES (8, 4, 'Denis',      'Nalivko',      3);

