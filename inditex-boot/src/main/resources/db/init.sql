CREATE SCHEMA IF NOT EXISTS inditex;

CREATE TABLE inditex.currencies (
    id VARCHAR(3) PRIMARY KEY
);

CREATE TABLE inditex.brands (
    id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE inditex.products (
    id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE inditex.prices (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    brand_id INTEGER NOT NULL,
    start_date timestamp NOT NULL,
    end_date timestamp NOT NULL,
    price_list INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    priority INTEGER NOT NULL,
    price DECIMAL(6, 2) NOT NULL,
    currency_id VARCHAR(3) NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES inditex.brands(id),
    FOREIGN KEY (product_id) REFERENCES inditex.products(id),
    FOREIGN KEY (currency_id) REFERENCES inditex.currencies(id)
);

INSERT INTO inditex.currencies (id) VALUES ('EUR');
INSERT INTO inditex.currencies (id) VALUES ('USD');

INSERT INTO inditex.brands (id, name) VALUES (1, 'ZARA');
INSERT INTO inditex.brands (id, name) VALUES (2, 'MANGO');
INSERT INTO inditex.brands (id, name) VALUES (3, 'LEFTIES');

INSERT INTO inditex.products (id, name) VALUES (35455, 'Pantalón negro');
INSERT INTO inditex.products (id, name) VALUES (35456, 'Jersey blanco');

INSERT INTO inditex.prices (brand_id, start_date, end_date, price_list, product_id, priority, price, currency_id)
VALUES (1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
(1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
(1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR'),
(1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR');
