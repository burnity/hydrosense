###### Atividade - Aplicando Técnicas de DevOps
# HydroSense - Monitoramento ESG de Consumo de Água

O **HydroSense** é uma solução para gestão sustentável de recursos hídricos, alinhada aos princípios de ESG. Este projeto aplica práticas avançadas de **DevOps** e **DevSecOps** para garantir um ciclo de vida de software robusto e seguro.


## Instrução de como executar localmente com Docker:

1. **Configuração de Ambiente:**
   Copie o arquivo `.env.example` para `.env` e ajuste as variáveis se necessário.
2. **Subida da Infraestrutura:**
   ```bash
   docker compose up --build -d
   ```

**Endpoints Principais:**

- Listar Unidades: `GET http://localhost:8080/api/unidades`

##  Pipeline CI/CD (GitHub Actions)
Implementamos um pipeline automatizados conforme solicitado na atividade.).

- **Build:** Compilação com Maven e Java 17.
- **Test:** Execução de testes integrados. Utilizamos Github Services para subir um PostgreSQL o durante os testes, garantindo paridade entre desenvolvimento e teste.
- **Deploy (Staging/Prod):** Simulação de entrega em múltiplos ambientes.


##  Containerização e Orquestração
- **Dockerfile:** Utiliza Multi-stage Build para reduzir a superfície de ataque e otimizar o tamanho da imagem final.
- **Docker Compose:** Orquestra a aplicação e o banco de dados PostgreSQL em uma rede privada virtualizada.

##  Tecnologias Utilizadas
- Java 17 / Spring Boot 3.5.7
- PostgreSQL 15.5-alpine
- Flyway (Migrações de Schema)
- Docker & Docker Compose
- GitHub Actions

##  Decisões Estratégicas (DevSecOps)
O projeto originalmente tinha como database o da Fiap Oracle, optamos pela migração para PostgreSQL Containerizado.

**Motivos:**

- Segurança: Evita o compartilhamento de credenciais reais de banco de dados no repositório.
- Portabilidade: Garante que o avaliador consiga rodar o projeto sem conflitos de rede ou indisponibilidade de servidores externos.
- Externalização: Toda a configuração sensível é injetada via variáveis de ambiente, mantendo o histórico do Git limpo de segredos.