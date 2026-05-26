package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.UserModel;
import util.SenhaUtil;

public class UserDAO {

    public UserModel validarLogin(UserModel userModel) {
        String sql = "SELECT username, psw, funcao FROM users WHERE username = ?";

        Connection con = ConnectionFactory.getConnection();

        if (con == null) {
            System.out.println("ERRO: conexão com banco retornou null");
            return null;
        }

        try (con;
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, userModel.getUsername());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashBanco = rs.getString("psw");

                    System.out.println("Usuário encontrado: " + userModel.getUsername());
                    System.out.println("Hash do banco: " + hashBanco);

                    boolean senhaValida = SenhaUtil.verificarSenha(
                            userModel.getPassword(),
                            hashBanco
                    );

                    System.out.println("Senha válida: " + senhaValida);

                    if (senhaValida) {
                        UserModel user = new UserModel();
                        user.setUsername(rs.getString("username"));
                        user.setPassword(hashBanco);
                        user.setFuncao(rs.getString("funcao"));
                        return user;
                    }
                } else {
                    System.out.println("ERRO: usuário não encontrado no banco: " + userModel.getUsername());
                }
            }
            return null;

        } catch (Exception e) {
            System.out.println("ERRO na validação do login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}