package controller;

import com.google.gson.Gson;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import model.CadastroProdutoModel;

@WebServlet("/api/produto/atualizar")
public class AtualizarProdutoController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> resultado = new HashMap<>();
        Gson gson = new Gson();

        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.trim().isEmpty()) {
                resultado.put("success", false);
                resultado.put("message", "Id do produto não informado.");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }

            String codigoBarras = request.getParameter("codigoBarras");
            if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
                resultado.put("success", false);
                resultado.put("message", "Código de barras é obrigatório.");
                response.getWriter().write(gson.toJson(resultado));
                return;
            }

            String dataFabricacaoStr = request.getParameter("dataFabricacao");
            String dataVencimentoStr = request.getParameter("dataVencimento");

            if (dataFabricacaoStr != null && !dataFabricacaoStr.trim().isEmpty()
                    && dataVencimentoStr != null && !dataVencimentoStr.trim().isEmpty()) {
                try {
                    LocalDate dataFabricacao = LocalDate.parse(dataFabricacaoStr);
                    LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);

                    if (dataVencimento.isBefore(dataFabricacao)) {
                        resultado.put("success", false);
                        resultado.put("message", "Data de vencimento não pode ser anterior à data de fabricação.");
                        response.getWriter().write(gson.toJson(resultado));
                        return;
                    }
                } catch (DateTimeParseException e) {
                    resultado.put("success", false);
                    resultado.put("message", "Data de fabricação ou vencimento inválida.");
                    response.getWriter().write(gson.toJson(resultado));
                    return;
                }
            }

            CadastroProdutoModel produto = new CadastroProdutoModel();
            produto.setId(Integer.parseInt(idStr));
            produto.setCodigoBarras(codigoBarras);
            produto.setNomeProduto(request.getParameter("nomeProduto"));
            produto.setFabricante(request.getParameter("fabricante"));
            produto.setMarca(request.getParameter("marca"));
            produto.setDataFabricacao(dataFabricacaoStr);
            produto.setDataVencimento(dataVencimentoStr);
            produto.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
            produto.setValor(request.getParameter("valor"));
            produto.setTotal(request.getParameter("total"));
            produto.setStatus(request.getParameter("status"));
            produto.setPrateleira(request.getParameter("prateleira"));

            String estoqueMinStr = request.getParameter("estoqueMinimo");
            if (estoqueMinStr != null && !estoqueMinStr.trim().isEmpty()) {
                produto.setEstoqueMinimo(Long.parseLong(estoqueMinStr.trim()));
            } else {
                produto.setEstoqueMinimo(5);
            }

            CadastroProdutosDAO dao = new CadastroProdutosDAO();
            boolean sucesso = dao.atualizar(produto);

            resultado.put("success", sucesso);
            resultado.put("message", sucesso ? "Produto atualizado com sucesso!" : "Erro ao atualizar produto no banco de dados.");

        } catch (NumberFormatException e) {
            resultado.put("success", false);
            resultado.put("message", "Valores numéricos inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            resultado.put("success", false);
            resultado.put("message", "Erro inesperado ao atualizar produto.");
        }

        response.getWriter().write(gson.toJson(resultado));
    }
}
