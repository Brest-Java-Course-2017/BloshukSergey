DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
  customer_id       INT           NOT NULL AUTO_INCREMENT,
  session_id        INT           NOT NULL,
  first_name        VARCHAR(30)   NOT NULL,
  last_name         VARCHAR(30)   NOT NULL,
  quantity_tickets  INT           NOT NULL,
  PRIMARY KEY (customer_id),
  FOREIGN KEY (session_id) REFERENCES session(session_id) ON DELETE CASCADE ON UPDATE CASCADE
);