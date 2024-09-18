CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    available_quantity INTEGER NOT NULL,
    product_image VARCHAR(255),
    category VARCHAR(100)
);
