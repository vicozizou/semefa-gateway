-- User table
CREATE TABLE IF NOT EXISTS app_user (
    id INT AUTO_INCREMENT,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(128) NOT NULL,
    encrypted BOOL NOT NULL,
    status VARCHAR(16) NOT NULL,
    PRIMARY KEY (id)
);

-- Role table
CREATE TABLE IF NOT EXISTS app_role (
    id VARCHAR(16),
    PRIMARY KEY (id)
);

-- User Role table
CREATE TABLE IF NOT EXISTS user_role (
    user_id INT,
    role_id VARCHAR(16),
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES app_user (id)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    FOREIGN KEY (role_id)
        REFERENCES app_role (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);
