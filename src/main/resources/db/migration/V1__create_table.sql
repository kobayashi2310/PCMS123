CREATE TABLE IF NOT EXISTS spring_session (
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
    ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON spring_session (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON spring_session (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON spring_session (PRINCIPAL_NAME);

CREATE TABLE spring_session_attributes (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES spring_session(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    number CHAR(6) NOT NULL,
    name VARCHAR(100) NOT NULL,
    kana VARCHAR(100) NOT NULL,
    password VARCHAR(256) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL,
    UNIQUE (number),
    UNIQUE (email),
    CHECK ( role IN ('student', 'teacher'))
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pc (
    pc_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    serial_number VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    UNIQUE (serial_number),
    CHECK ( status IN ('available', 'maintenance', 'take_out'))
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS period (
    period_number TINYINT PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS reservation (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    pc_id INT NOT NULL,
    date DATE NOT NULL,
    period_number TINYINT NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending_approval',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approver INT DEFAULT NULL,
    approval_time TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (pc_id) REFERENCES pc(pc_id),
    FOREIGN KEY (period_number) REFERENCES period(period_number),
    FOREIGN KEY (approver) REFERENCES user(user_id),
    CHECK ( status IN ('pending_approval', 'approval', 'cancelled'))
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS returned (
    returned_id INT PRIMARY KEY AUTO_INCREMENT,
    reservation_id INT NOT NULL,
    returned_time TIMESTAMP NOT NULL,
    comment VARCHAR(500),
    FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id)
) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- notification テーブルはコメントアウトのままです。
-- CREATE TABLE IF NOT EXISTS notification (
--     notification_id INT PRIMARY KEY AUTO_INCREMENT,
--     receiver_id INT NOT NULL,
--     sender_id INT, -- NULLの場合はシステム通知
--     content TEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     is_read BOOLEAN DEFAULT FALSE,
--     FOREIGN KEY (receiver_id) REFERENCES user(user_id),
--     FOREIGN KEY (sender_id) REFERENCES user(user_id)
-- ) DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
