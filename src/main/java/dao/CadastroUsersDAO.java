package dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import model.CadastroUsuarioModel;
import util.SenhaUtil;

public class CadastroUsersDAO {

    public boolean cadastrar(CadastroUsuarioModel user) {
        String sql = "INSERT INTO users (username, namefirst, sobreNome, matricula, CPF, psw, sexo, dtaNascimento, email, telefone, funcao, cep, endereco, numero, complemento, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // stmt dentro do try garante que ele feche sozinho mesmo se der erro
        try (var con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            String senhaHash = SenhaUtil.gerarHash(user.getSenha());

            stmt.setString(1, user.getNomeUsuario());
            stmt.setString(2, user.getNome());
            stmt.setString(3, user.getSobrenome());
            stmt.setString(4, user.getMatricula());
            stmt.setString(5, user.getCpf());
            stmt.setString(6, senhaHash);
            stmt.setString(7, user.getSexo());
            stmt.setString(8, user.getData()); // Certifique-se que o formato é AAAA-MM-DD
            stmt.setString(9, user.getEmail());
            stmt.setString(10, user.getTelefone());
            stmt.setString(11, user.getFuncao());
            stmt.setString(12, user.getCep());
            stmt.setString(13, user.getEndereco());
            stmt.setLong(14, user.getNumero());
            stmt.setString(15, user.getComplemento());
            stmt.setString(16, user.getBairro());
            stmt.setString(17, user.getCidade());
            stmt.setString(18, user.getEstado());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }
}
