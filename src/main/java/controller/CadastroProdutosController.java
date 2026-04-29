package controller;

import dao.CadastroProdutosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.CadastroProdutoModel;

@WebServlet("/cadastroProdutos")
public class CadastroProdutosController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CadastroProdutoModel produto = new CadastroProdutoModel();

        // VALIDAÇÃO: verificar se o código de barras foi enviado
        String codigoBarras = request.getParameter("codigoBarras");
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            System.out.println("ERRO: Código de barras não informado!");
            response.sendRedirect(request.getContextPath() + "/pages/cadastroProdutos.html?erro=codigo");
            return;
        }
        
        produto.setCodigoBarras(codigoBarras);
        produto.setNomeProduto(request.getParameter("nomeProduto"));
        produto.setFabricante(request.getParameter("fabricante"));
        produto.setMarca(request.getParameter("marca"));
        produto.setDataFabricacao(request.getParameter("dataFabricacao"));
        produto.setDataVencimento(request.getParameter("dataVencimento"));
        produto.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
        produto.setValor(request.getParameter("valor"));
        produto.setTotal(request.getParameter("total"));
        produto.setStatus(request.getParameter("status"));

        // DEBUG: imprimir o que está sendo salvo
        System.out.println("=== SALVANDO PRODUTO ===");
        System.out.println("Código Barras: " + produto.getCodigoBarras());
        System.out.println("Nome: " + produto.getNomeProduto());
        System.out.println("Status: " + produto.getStatus());

        CadastroProdutosDAO dao = new CadastroProdutosDAO();

        if (dao.salvar(produto)) {
            System.out.println("Produto salvo com sucesso!");
            response.sendRedirect(request.getContextPath() + "/pages/dashboard.html");
        } else {
            System.out.println("ERRO ao salvar produto!");
            response.sendRedirect(request.getContextPath() + "/pages/cadastroProdutos.html?erro=banco");
        }
    }
}
