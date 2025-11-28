CREATE TABLE IF NOT EXISTS filmes (
    id INTEGER PRIMARY KEY, 
    titulo TEXT NOT NULL,
    sinopse TEXT,
    lancamento TEXT,
    nota REAL
);