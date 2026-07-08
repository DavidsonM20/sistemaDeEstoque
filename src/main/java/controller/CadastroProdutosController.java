package controller;

import dao.CadastroProdutosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        String dataFabricacaoStr = request.getParameter("dataFabricacao");
        String dataVencimentoStr = request.getParameter("dataVencimento");

        // VALIDAÇÃO: data de vencimento não pode ser anterior à data de fabricação
        if (dataFabricacaoStr != null && !dataFabricacaoStr.trim().isEmpty()
                && dataVencimentoStr != null && !dataVencimentoStr.trim().isEmpty()) {
            try {
                LocalDate dataFabricacao = LocalDate.parse(dataFabricacaoStr);
                LocalDate dataVencimento = LocalDate.parse(dataVencimentoStr);

                if (dataVencimento.isBefore(dataFabricacao)) {
                    System.out.println("ERRO: Data de vencimento anterior à data de fabricação!");
                    response.sendRedirect(request.getContextPath() + "/pages/cadastroProdutos.html?erro=vencimento");
                    return;
                }
            } catch (DateTimeParseException e) {
                System.out.println("ERRO: Data de fabricação ou vencimento inválida!");
                response.sendRedirect(request.getContextPath() + "/pages/cadastroProdutos.html?erro=data");
                return;
            }
        }

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

        // DEBUG: imprimir o que está sendo salvo
        System.out.println("=== SALVANDO PRODUTO ===");
        System.out.println("Código Barras: " + produto.getCodigoBarras());
        System.out.println("Nome: " + produto.getNomeProduto());
        System.out.println("Status: " + produto.getStatus());
        System.out.println("Prateleira: " + produto.getPrateleira());
        System.out.println("Estoque Mínimo: " + produto.getEstoqueMinimo());

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
