package model;

public class CadastroUsuarioModel {
    private int id;
    private String username;
    private String psw;
    private String namefirst;
    private String sobreNome;
    private String cpf;
    private String matricula;
    private String sexo;
    private String dtaNascimento;
    private String email;
    private String telefone;
    private String funcao;
    private String cep;
    private String endereco;
    private long numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    public CadastroUsuarioModel() {
    }

    // --- Getters e Setters Corrigidos ---

    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }

    public String getNomeUsuario() { return username; }
    
    public void setNomeUsuario(String username) { this.username = username; }

    public String getSenha() { return psw; }
    
    public void setSenha(String psw) { this.psw = psw; }

    public String getNome() { return namefirst; }
    
    public void setNome(String nome) { this.namefirst = nome; }

    public String getSobrenome() { return sobreNome; }
    
    public void setSobrenome(String sobreNome) { this.sobreNome = sobreNome; }

    public String getCpf() { return cpf; }
    
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getMatricula() { return matricula; }
    
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getSexo() { return sexo; }
    
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getData() { return dtaNascimento; }
    
    public void setData(String data) { this.dtaNascimento = data; }

    public String getEmail() { return email; }
    
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getFuncao() { return funcao; }
    
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getCep() { return cep; }
    
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public long getNumero() { return numero; }
    
    public void setNumero(long numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    
    public void setEstado(String estado) { this.estado = estado; }
}