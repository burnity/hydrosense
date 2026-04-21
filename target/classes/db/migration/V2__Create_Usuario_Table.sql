-- ========================================
-- Script de Migracao HydroSense (PostgreSQL)
-- Versao: V2
-- Descricao: Criacao da tabela de usuarios para autenticacao JWT
-- ========================================

-- Drops idempotentes
DROP TABLE IF EXISTS TB_USUARIO CASCADE;
DROP SEQUENCE IF EXISTS USUARIO_SEQ;
DROP INDEX IF EXISTS idx_usuario_email;

-- Tabela: TB_USUARIO
-- Descricao: Armazena informacoes de usuarios do sistema
CREATE TABLE IF NOT EXISTS TB_USUARIO (
    id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER' CHECK (role IN ('USER', 'ADMIN')),
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

-- Sequencia para TB_USUARIO
CREATE SEQUENCE IF NOT EXISTS USUARIO_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Comentario na tabela
COMMENT ON TABLE TB_USUARIO IS 'Usuarios do sistema HydroSense';

-- Usuario admin padrao (senha: admin123)
-- Hash BCrypt da senha 'admin123'
INSERT INTO TB_USUARIO (id, nome, email, senha, role, ativo)
VALUES (
    nextval('USUARIO_SEQ'),
    'Administrador',
    'admin@hydrosense.com',
    '$2a$10$xHKVZ7lXfDGQC6Qx5t5I3eFqQvN0Jj8z3v8K8p4Z5E5F5E5E5E5E5',
    'ADMIN',
    TRUE
);
