CREATE TABLE writers.labels (
    id INT NOT NULL UNIQUE AUTO_INCREMENT,
	name VARCHAR(255),
	status ENUM('ACTIVE', 'DELETED'),
	PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
