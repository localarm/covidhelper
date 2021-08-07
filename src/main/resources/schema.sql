CREATE TABLE units(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE departments(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    unit_id BIGINT NOT NULL,
    FOREIGN KEY(unit_id) REFERENCES units(id)
);

CREATE TABLE divisions(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY(department_id) REFERENCES departments(id)
);

CREATE TABLE employees(
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(60) NOT NULL,
    lastname VARCHAR(60) NOT NULL,
    secondname VARCHAR(60) NOT NULL,
    address VARCHAR(60) NOT NULL,
    post VARCHAR(60) NOT NULL,
    status VARCHAR(60) NOT NULL,
    division_id BIGINT NOT NULL,
    FOREIGN KEY(division_id) REFERENCES divisions(id)
);

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    authority VARCHAR(60) NOT NULL,
    department_id BIGINT NOT NULL,
    FOREIGN KEY(department_id) REFERENCES departments(id)
);