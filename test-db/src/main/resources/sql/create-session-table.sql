DROP TABLE IF EXISTS session;
CREATE TABLE session (
  session_id    INT           NOT NULL AUTO_INCREMENT,
  movie_name    VARCHAR(30)   NOT NULL,
  session_date  DATE          NOT NULL,
  PRIMARY KEY (session_id)
);