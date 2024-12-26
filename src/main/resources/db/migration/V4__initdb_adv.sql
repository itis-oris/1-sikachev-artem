CREATE TABLE advertisements (
    id SERIAL PRIMARY KEY,
    sender_id BIGINT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    publish_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image_url TEXT,
    status VARCHAR(20)
);