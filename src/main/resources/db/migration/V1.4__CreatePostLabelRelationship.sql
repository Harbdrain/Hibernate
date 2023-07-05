CREATE TABLE writers.post_label_relationship (
	post_id INT NOT NULL,
	label_id INT NOT NULL,
	FOREIGN KEY(post_id) REFERENCES posts(id),
	FOREIGN KEY(label_id) REFERENCES labels(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;
