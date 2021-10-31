CREATE SCHEMA IF NOT EXISTS rest_api;

CREATE TABLE IF NOT EXISTS rest_api.gift_certificates
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    description text,
    price integer,
    duration integer,
    last_update_date timestamp with time zone,
    name character varying(20),
    create_date timestamp with time zone,
    CONSTRAINT gift_certificates_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rest_api.tags
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying(20),
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rest_api.certificate_tag_maps
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    gift_certificate_id integer,
    tag_id integer,
    CONSTRAINT gift_certificate_id FOREIGN KEY (gift_certificate_id)
        REFERENCES rest_api.gift_certificates (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT tag_id FOREIGN KEY (tag_id)
        REFERENCES rest_api.tags (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS rest_api.users
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    forename character varying(15),
    surname character varying(15),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rest_api.orders
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    purchase_timestamp timestamp with time zone,
    total_cost integer,
    user_id integer,
    certificate_id integer,
    CONSTRAINT certificate_id FOREIGN KEY (certificate_id)
        REFERENCES rest_api.gift_certificates (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES rest_api.users (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO rest_api.gift_certificates
    (description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');
INSERT INTO rest_api.gift_certificates
(description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');
INSERT INTO rest_api.gift_certificates
(description, price, duration, last_update_date, name, create_date)
VALUES ('qwerty', 99, 3, '2017-01-23T12:34:56.123456789Z', 'qwerty', '2017-01-23T12:34:56.123456789Z');

INSERT INTO rest_api.tags (name) VALUES ('test');
INSERT INTO rest_api.tags (name) VALUES ('test');
INSERT INTO rest_api.tags (name) VALUES ('test');
INSERT INTO rest_api.tags (name) VALUES ('test');

INSERT INTO rest_api.certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO rest_api.certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 3);
INSERT INTO rest_api.certificate_tag_maps(gift_certificate_id, tag_id) VALUES (1, 4);

INSERT INTO rest_api.users(forename, surname) VALUES ('Jack', 'Smith');

INSERT INTO rest_api.orders(purchase_timestamp, total_cost, user_id, certificate_id)
VALUES ('2017-01-23T12:34:56.123456789Z', 99, 1, 1);