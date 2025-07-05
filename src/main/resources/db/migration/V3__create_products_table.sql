CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          account_number VARCHAR(255) UNIQUE NOT NULL,
                          balance DOUBLE PRECISION NOT NULL,
                          product_type VARCHAR(50) NOT NULL,
                          user_id BIGINT NOT NULL REFERENCES users(id)
);