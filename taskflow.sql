CREATE TABLE member (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);

CREATE TABLE task (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    creator_id BIGINT NOT NULL,
    manager_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    due_date DATETIME(6) NOT NULL,
    start_date DATETIME(6) NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME(6) NULL,
    FOREIGN KEY (creator_id) REFERENCES member(id),
    FOREIGN KEY (manager_id) REFERENCES member(id)
);

CREATE TABLE comment (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at DATETIME(6) NULL,
    FOREIGN KEY (task_id) REFERENCES  task(id),
    FOREIGN KEY (member_id) REFERENCES  member(id)
);

CREATE TABLE activity_log (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    activity_type VARCHAR(50) NOT NULL,
    target_id BIGINT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES  member(id)
);