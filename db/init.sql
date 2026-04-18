use estoque_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL, -- Mantido para o Login
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
    numero VARCHAR(10),
    complemento VARCHAR(100),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado CHAR(2)
);

create table produtos (
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
    status VARCHAR(255)
);

INSERT INTO users (username,namefirst,sobreNome,matricula,CPF,psw,sexo,dtaNascimento,email,telefone,funcao,cep,endereco,numero,complemento, 
    bairro,cidade,estado
) VALUES ('admin','Davidson','Miranda','ADM-001','000.000.000-00','1234','Masculino','2005-03-11','admin@sistema.com','(11) 99999-9999', 
          'Administrador','01000-000','Avenida Principal','100','Sala 01','Centro','São Paulo','SP'
);
