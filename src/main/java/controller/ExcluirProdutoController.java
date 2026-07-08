package controller;

import com.google.gson.Gson;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/produto/excluir")
public class ExcluirProdutoController extends HttpServlet {

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

            int id = Integer.parseInt(idStr);
            CadastroProdutosDAO dao = new CadastroProdutosDAO();
            boolean sucesso = dao.excluir(id);

            resultado.put("success", sucesso);
            resultado.put("message", sucesso ? "Produto excluído com sucesso!" : "Não foi possível excluir o produto.");

        } catch (NumberFormatException e) {
            resultado.put("success", false);
            resultado.put("message", "Id inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            resultado.put("success", false);
            resultado.put("message", "Erro inesperado ao excluir produto.");
        }

        response.getWriter().write(gson.toJson(resultado));
    }
}
