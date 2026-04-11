package dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import model.CadastroUsuarioModel;

public class CadastroUsersDAO {

    public boolean cadastrar(CadastroUsuarioModel user) {
        // Corrigido: VALUES com 'S' e ordem mapeada corretamente
        String sql = "INSERT INTO users " +
                "(username, namefirst, sobreNome, matricula, CPF, psw, sexo, dtaNascimento, email, telefone, funcao, cep, endereco, numero, complemento, bairro, cidade, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var con = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);

            // A ordem aqui segue EXATAMENTE a ordem dos parênteses da String SQL acima
            stmt.setString(1, user.getNomeUsuario()); // username
            stmt.setString(2, user.getNome());        // namefirst
            stmt.setString(3, user.getSobrenome());   // sobreNome
            stmt.setString(4, user.getMatricula());   // matricula
            stmt.setString(5, user.getCpf());         // CPF
            stmt.setString(6, user.getSenha());       // psw
            stmt.setString(7, user.getSexo());        // sexo
            stmt.setString(8, user.getData());        // dtaNascimento
            stmt.setString(9, user.getEmail());       // email
            stmt.setString(10, user.getTelefone());   // telefone
            stmt.setString(11, user.getFuncao());     // funcao
            stmt.setString(12, user.getCep());        // cep
            stmt.setString(13, user.getEndereco());   // endereco
            stmt.setLong(14, user.getNumero());       // numero
            stmt.setString(15, user.getComplemento());// complemento
            stmt.setString(16, user.getBairro());     // bairro
            stmt.setString(17, user.getCidade());     // cidade
            stmt.setString(18, user.getEstado());     // estado

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}