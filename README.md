[Adisson Pires Da Silva Filho] Disciplina: Programação Orientada a Objetos / Desenvolvimento Backend
# 🎬 Sistema de Gerenciamento de Filmes (Integração TMDB + SQLite)

Este projeto é uma aplicação Java que consome a API pública do **The Movie Database (TMDB)** para buscar informações sobre filmes e permite ao usuário criar uma biblioteca pessoal local, salvando os dados em um banco de dados relacional **SQLite**.

O sistema implementa um **CRUD completo** (Create, Read, Update, Delete) e segue o padrão de arquitetura em camadas (MVC/DAO).

## 🚀 Funcionalidades

- **Buscar Filmes (API):** Conecta-se à API do TMDB para buscar títulos, sinopses e datas de lançamento reais.
- **Salvar (Create):** Persiste os filmes escolhidos no banco de dados local.
- **Listar (Read):** Exibe todos os filmes salvos na biblioteca pessoal.
- **Atualizar (Update):** Permite editar a nota pessoal e a sinopse/comentários de um filme salvo.
- **Excluir (Delete):** Remove filmes do banco de dados local.

## 🛠️ Tecnologias Utilizadas

- **Java 17** (Linguagem base)
- **Maven** (Gerenciador de dependências)
- **SQLite JDBC** (Banco de dados relacional embarcado)
- **Gson** (Biblioteca para conversão de JSON para Objetos Java)
- **Java HttpClient** (Para requisições HTTP modernas)

## 📂 Estrutura do Projeto

O projeto está organizado seguindo boas práticas de orientação a objetos:

- `src/model`: Classes que representam as entidades (ex: `Filme`).
- `src/dao`: Camada de acesso a dados (JDBC e SQL).
- `src/service`: Lógica de consumo da API externa.
- `src/view` (ou raiz): Interface de console com o usuário (`Main`).

## ⚙️ Pré-requisitos e Configuração

Para executar este projeto, você precisará de:

1.  **JDK 11 ou superior** instalado.
2.  Uma IDE Java (Eclipse, IntelliJ, VS Code).
3.  Uma **Chave de API (API Key)** do TMDB.

### 🔑 Configurando a Chave da API

Antes de rodar, é necessário inserir sua chave de acesso no código:

1.  Abra o arquivo `src/service/TmdbService.java`.
2.  Localize a constante `API_KEY`.
3.  Substitua o valor pelo seu token:
    ```java
    private static final String API_KEY = "SUA_CHAVE_DO_TMDB_AQUI";
    ```

## ▶️ Como Executar

1.  **Clonar/Baixar** o projeto.
2.  Importar como **Projeto Maven** na sua IDE.
3.  Aguardar o download das dependências (Gson e SQLite).
4.  Executar a classe `Main.java`.
5.  O banco de dados (`banco.db`) e a tabela serão criados automaticamente na primeira execução.

## 📝 Script SQL

Embora o sistema crie a tabela automaticamente via código Java, o script SQL original da estrutura encontra-se no arquivo `banco.sql` na raiz do projeto:

```sql
CREATE TABLE IF NOT EXISTS filmes (
    id INTEGER PRIMARY KEY, 
    titulo TEXT,
    sinopse TEXT,
    lancamento TEXT,
    nota REAL
);