package dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Boa prática especificar a exceção
import model.UserModel;
import util.SenhaUtil;

public class UserDAO {

    // Alterado de boolean para UserModel para permitir retornar os dados do usuário logado
    public UserModel validarLogin(UserModel userModel) {
        // Adicionadas as colunas necessárias na busca
        String sql = "SELECT username, psw, funcao FROM users WHERE username = ?";

        try (var con = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, userModel.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashBanco = rs.getString("psw");
                    
                    // Verifica se a senha digitada bate com o hash do banco
                    boolean senhaValida = SenhaUtil.verificarSenha(userModel.getPassword(), hashBanco);
                    
                    if (senhaValida) {
                        UserModel user = new UserModel();
                        // Correção: Usar SET para atribuir valores e não GET
                        user.setUsername(rs.getString("username"));
                        user.settPassword(hashBanco);
                        user.setFuncao(rs.getString("funcao"));
                        
                        return user; // Retorna o objeto preenchido
                    }
                }
            }
            return null; // Retorna null se a senha estiver errada ou usuário não existir

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}