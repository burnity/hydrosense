-- ========================================
-- Script de Migracao HydroSense (PostgreSQL)
-- Versao: V1
-- Descricao: Criacao das tabelas principais do sistema
-- ========================================

-- Tabela: TB_UNIDADE_CONSUMO
-- Descricao: Armazena informacoes sobre unidades de consumo (residencias, comercios)
CREATE TABLE IF NOT EXISTS TB_UNIDADE_CONSUMO (
    id BIGINT NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('RESIDENCIAL', 'COMERCIAL')),
    numero_habitantes INTEGER,
    area_m2 NUMERIC(10,2),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_unidade_consumo PRIMARY KEY (id)
);

-- Sequencia para TB_UNIDADE_CONSUMO
CREATE SEQUENCE IF NOT EXISTS UNIDADE_CONSUMO_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Tabela: TB_REGISTRO_LEITURA
-- Descricao: Armazena os registros de leitura de consumo de agua
CREATE TABLE IF NOT EXISTS TB_REGISTRO_LEITURA (
    id BIGINT NOT NULL,
    unidade_id BIGINT NOT NULL,
    volume_m3 NUMERIC(10,3) NOT NULL,
    data_hora_leitura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fonte_leitura VARCHAR(50) DEFAULT 'SENSOR',
    observacao VARCHAR(500),
    CONSTRAINT pk_registro_leitura PRIMARY KEY (id),
    CONSTRAINT fk_leitura_unidade FOREIGN KEY (unidade_id)
        REFERENCES TB_UNIDADE_CONSUMO(id) ON DELETE CASCADE
);

-- Sequencia para TB_REGISTRO_LEITURA
CREATE SEQUENCE IF NOT EXISTS REGISTRO_LEITURA_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Tabela: TB_ALERTA
-- Descricao: Armazena alertas de consumo excessivo
CREATE TABLE IF NOT EXISTS TB_ALERTA (
    id BIGINT NOT NULL,
    unidade_id BIGINT NOT NULL,
    leitura_id BIGINT,
    tipo_alerta VARCHAR(50) NOT NULL,
    mensagem VARCHAR(500) NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVO' CHECK (status IN ('ATIVO', 'RESOLVIDO', 'IGNORADO')),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_resolucao TIMESTAMP,
    CONSTRAINT pk_alerta PRIMARY KEY (id),
    CONSTRAINT fk_alerta_unidade FOREIGN KEY (unidade_id)
        REFERENCES TB_UNIDADE_CONSUMO(id) ON DELETE CASCADE,
    CONSTRAINT fk_alerta_leitura FOREIGN KEY (leitura_id)
        REFERENCES TB_REGISTRO_LEITURA(id) ON DELETE SET NULL
);

-- Sequencia para TB_ALERTA
CREATE SEQUENCE IF NOT EXISTS ALERTA_SEQ
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Indices para melhor performance
CREATE INDEX IF NOT EXISTS idx_leitura_unidade ON TB_REGISTRO_LEITURA(unidade_id);
CREATE INDEX IF NOT EXISTS idx_leitura_data ON TB_REGISTRO_LEITURA(data_hora_leitura);
CREATE INDEX IF NOT EXISTS idx_alerta_unidade ON TB_ALERTA(unidade_id);
CREATE INDEX IF NOT EXISTS idx_alerta_status ON TB_ALERTA(status);

-- Dados iniciais para teste
INSERT INTO TB_UNIDADE_CONSUMO (id, endereco, tipo, numero_habitantes, area_m2, ativo)
VALUES (nextval('UNIDADE_CONSUMO_SEQ'), 'Av. Paulista, 1000 - Sao Paulo/SP', 'RESIDENCIAL', 4, 120.50, TRUE);

INSERT INTO TB_UNIDADE_CONSUMO (id, endereco, tipo, numero_habitantes, area_m2, ativo)
VALUES (nextval('UNIDADE_CONSUMO_SEQ'), 'Rua Oscar Freire, 500 - Sao Paulo/SP', 'COMERCIAL', 0, 350.00, TRUE);
