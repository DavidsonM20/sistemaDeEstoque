package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CadastroProdutoModel;

public class CadastroProdutosDAO {

    public boolean salvar(CadastroProdutoModel produto) {
        if (produto.getCodigoBarras() == null || produto.getCodigoBarras().trim().isEmpty()) {
            System.err.println("ERRO: Tentativa de salvar produto sem código de barras!");
            return false;
        }

        String sql = "INSERT INTO produtos "
                + "(codigo_barras, nome_produto, fabricante, marca, data_fabricacao, data_vencimento, quantidade, valor, total, status, prateleira, estoque_minimo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getCodigoBarras());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setString(3, produto.getFabricante());
            stmt.setString(4, produto.getMarca());
            stmt.setDate(5, java.sql.Date.valueOf(produto.getDataFabricacao()));
            stmt.setDate(6, java.sql.Date.valueOf(produto.getDataVencimento()));
            stmt.setLong(7, produto.getQuantidade());
            stmt.setString(8, produto.getValor());
            stmt.setString(9, produto.getTotal());
            stmt.setString(10, produto.getStatus());
            stmt.setString(11, produto.getPrateleira());
            stmt.setLong(12, produto.getEstoqueMinimo());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CadastroProdutoModel> listarComFiltro(String nome, String tipo, String data) {
        List<CadastroProdutoModel> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM produtos WHERE 1=1");

        if (nome != null && !nome.isEmpty()) {
            sql.append(" AND LOWER(nome_produto) LIKE ?");
        }
        if (tipo != null && !tipo.isEmpty()) {
            sql.append(" AND status = ?");
        }
        if (data != null && !data.isEmpty()) {
            sql.append(" AND data_vencimento = ?");
        }

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (nome != null && !nome.isEmpty()) {
                stmt.setString(index++, "%" + nome.toLowerCase() + "%");
            }
            if (tipo != null && !tipo.isEmpty()) {
                stmt.setString(index++, tipo);
            }
            if (data != null && !data.isEmpty()) {
                stmt.setString(index++, data);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CadastroProdutoModel p = new CadastroProdutoModel();
                    p.setCodigoBarras(rs.getString("codigo_barras"));
                    p.setNomeProduto(rs.getString("nome_produto"));
                    p.setFabricante(rs.getString("fabricante"));
                    p.setMarca(rs.getString("marca"));

                    java.sql.Date dataFab = rs.getDate("data_fabricacao");
                    java.sql.Date dataVen = rs.getDate("data_vencimento");
                    p.setDataFabricacao(dataFab != null ? dataFab.toLocalDate().toString() : "");
                    p.setDataVencimento(dataVen != null ? dataVen.toLocalDate().toString() : "");

                    p.setQuantidade(rs.getLong("quantidade"));
                    p.setValor(rs.getString("valor"));
                    p.setTotal(rs.getString("total"));
                    p.setStatus(rs.getString("status"));
                    p.setPrateleira(rs.getString("prateleira"));
                    p.setEstoqueMinimo(rs.getLong("estoque_minimo"));

                    lista.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
