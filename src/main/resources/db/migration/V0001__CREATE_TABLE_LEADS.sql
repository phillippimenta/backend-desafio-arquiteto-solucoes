CREATE SEQUENCE leads_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

CREATE TABLE leads
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('leads_id_seq'),
    name          VARCHAR(255) NOT NULL,
    cpf           VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone         VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    consent_given BOOLEAN      NOT NULL,
    consent_ip    VARCHAR(255) NOT NULL
);

ALTER SEQUENCE leads_id_seq OWNED BY leads.id;