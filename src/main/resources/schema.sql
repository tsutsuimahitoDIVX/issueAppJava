CREATE TABLE IF NOT EXISTS users (
    id       INT             NOT NULL AUTO_INCREMENT,
    username VARCHAR(128)    NOT NULL UNIQUE,
    password VARCHAR(512)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS issues (
    id         INT          NOT NULL AUTO_INCREMENT,
    title      VARCHAR(256) NOT NULL,
    content    VARCHAR(256) NOT NULL,
    period     VARCHAR(256) NOT NULL,
    importance VARCHAR(256) NOT NULL,
    user_id    INT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
   id       INT          NOT NULL AUTO_INCREMENT,
   message  VARCHAR(256) NOT NULL,
   user_id  INT          NOT NULL,
   issue_id INT          NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (user_id)  REFERENCES users(id),
   FOREIGN KEY (issue_id) REFERENCES issues(id)
);
