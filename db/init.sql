use estoque_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    namefirst VARCHAR(100) NOT NULL,
    sobreNome VARCHAR(100) NOT NULL,
    matricula VARCHAR(100) NOT NULL,
    CPF VARCHAR(14) NOT NULL,
    psw VARCHAR(255) NOT NULL,
    sexo VARCHAR(20),
    dtaNascimento DATE,
    email VARCHAR(150),
    telefone VARCHAR(20),
    funcao VARCHAR(100),
    cep VARCHAR(10),
    endereco VARCHAR(255),
    numero INT,
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado CHAR(2)
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_barras VARCHAR(100) NOT NULL,
    nome_produto VARCHAR(255) NOT NULL,
    fabricante VARCHAR(255),
    marca VARCHAR(255),
    data_fabricacao DATE,
    data_vencimento DATE,
    quantidade BIGINT,
    valor DECIMAL(10,2),
    total DECIMAL(10,2),
    status VARCHAR(255),
    prateleira VARCHAR(100),
    estoque_minimo BIGINT DEFAULT 5
);

-- Usuário admin padrão (senha: password - troque após o primeiro login)
INSERT INTO users (username, namefirst, sobreNome, matricula, CPF, psw, sexo, dtaNascimento, email, telefone, funcao, cep, endereco, numero, complemento, bairro, cidade, estado)
VALUES (
    'admin',
    'Admin',
    'Sistema',
    'ADM-001',
    '000.000.000-00',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Masculino',
    '2000-01-01',
    'admin@sistema.com',
    '(00) 00000-0000',
    'ADMIN',
    '01000-000',
    'Rua Principal',
    1,
    '',
    'Centro',
    'São Paulo',
    'SP'
);

-- Usuário hugo com acesso limitado (senha: 1234)
-- Hash bcrypt de '1234'
INSERT INTO users (username, namefirst, sobreNome, matricula, CPF, psw, sexo, dtaNascimento, email, telefone, funcao, cep, endereco, numero, complemento, bairro, cidade, estado)
VALUES (
    'hugo',
    'Hugo',
    'Admin',
    'HUG-001',
    '111.111.111-11',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lh8a',
    'Masculino',
    '2000-01-01',
    'hugo@sistema.com',
    '(00) 00000-0000',
    'ADMIN_LIMITADO',
    '01000-000',
    'Rua Principal',
    1,
    '',
    'Centro',
    'São Paulo',
    'SP'
);
