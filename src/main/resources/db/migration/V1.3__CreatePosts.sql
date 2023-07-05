CREATE TABLE writers.posts (
	id INT NOT NULL UNIQUE AUTO_INCREMENT,
	content TEXT,
	created BIGINT,
	updated BIGINT,
	writer_id INT,
	status ENUM('ACTIVE', 'UNDER_REVIEW', 'DELETED'),
	PRIMARY KEY(id),
	FOREIGN KEY(writer_id) REFERENCES writers(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
