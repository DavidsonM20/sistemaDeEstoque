package controller;

import com.google.gson.Gson;
import connection.ConnectionFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/resumo")
public class ResumoEstoqueController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String sqlMovimentacao = """
                      SELECT
                          SUM(CASE WHEN status = 'entrada' THEN quantidade ELSE 0 END) AS entrada,
                          SUM(CASE WHEN status = 'saida' THEN quantidade ELSE 0 END) AS saida
                      FROM produtos
                      """;

        // Conta produtos distintos (por nome) cuja quantidade de entrada
        // menos saída está abaixo do estoque_minimo — mesma lógica de agregação
        // usada nos cards de entrada/saída
        String sqlReposicao = """
                      SELECT COUNT(*) AS precisam_reposicao FROM (
                          SELECT
                              nome_produto,
                              SUM(CASE WHEN status = 'entrada' THEN quantidade ELSE 0 END) -
                              SUM(CASE WHEN status = 'saida'   THEN quantidade ELSE 0 END) AS saldo,
                              MAX(estoque_minimo) AS minimo
                          FROM produtos
                          GROUP BY nome_produto
                      ) sub
                      WHERE saldo <= minimo
                      """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmtMov = conn.prepareStatement(sqlMovimentacao);
             ResultSet rsMov = stmtMov.executeQuery()) {

            int entrada = 0;
            int saida = 0;

            if (rsMov.next()) {
                entrada = rsMov.getInt("entrada");
                saida   = rsMov.getInt("saida");
            }

            int total = entrada - saida;

            int precisamReposicao = 0;
            try (PreparedStatement stmtRep = conn.prepareStatement(sqlReposicao);
                 ResultSet rsRep = stmtRep.executeQuery()) {
                if (rsRep.next()) {
                    precisamReposicao = rsRep.getInt("precisam_reposicao");
                }
            }

            Map<String, Integer> resultado = new HashMap<>();
            resultado.put("entrada", entrada);
            resultado.put("saida", saida);
            resultado.put("total", total);
            resultado.put("precisamReposicao", precisamReposicao);

            String json = new Gson().toJson(resultado);

            response.setContentType("application/json");
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
