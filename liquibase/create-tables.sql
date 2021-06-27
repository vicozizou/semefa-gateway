-- User table
CREATE TABLE IF NOT EXISTS app_user (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(16) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    encrypted BOOL NOT NULL,
    status VARCHAR(16) NOT NULL,
    PRIMARY KEY (id)
);

-- Role table
CREATE TABLE IF NOT EXISTS app_role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(16),
    user_id INT NOT NULL,
    UNIQUE(user_id, name),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES app_user (id)
);
