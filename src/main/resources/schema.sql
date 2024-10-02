CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loginType VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    imageUri VARCHAR(255),
    kakaoImageUri VARCHAR(255),
    red VARCHAR(255) DEFAULT '',
    yellow VARCHAR(255) DEFAULT '',
    green VARCHAR(255) DEFAULT '',
    blue VARCHAR(255) DEFAULT '',
    purple VARCHAR(255) DEFAULT '',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP,
    hashedRefreshToken VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    color VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    score INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP,
    userId BIGINT
);

CREATE TABLE IF NOT EXISTS favorite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    postId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP
);

CREATE TABLE IF NOT EXISTS image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uri VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deletedAt TIMESTAMP,
    postId BIGINT NOT NULL
);


