CREATE TABLE customer (
    customer_id INT 		NOT NULL AUTO_INCREMENT,
    name		VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (customer_id)
);


CREATE TABLE session (
    session_id		INT 		NOT NULL AUTO_INCREMENT,
    movie_name		VARCHAR(45) NOT NULL,
    session_date	DATE 		NOT NULL,
    total_seats	    INT 		NOT NULL,
    PRIMARY KEY (session_id)
  );


CREATE TABLE booking (
    session_id	INT NOT NULL,
    customer_id INT NOT NULL,
    PRIMARY KEY (session_id, customer_id),
    FOREIGN KEY (session_id)    REFERENCES  session     (session_id)    ON DELETE CASCADE,
    FOREIGN KEY (customer_id)   REFERENCES  customer    (customer_id)   ON DELETE CASCADE
);