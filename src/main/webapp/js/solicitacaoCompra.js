// -------------------------------------------------------
// Geração de Solicitação de Compra / Reposição de Estoque
// -------------------------------------------------------
// Reutiliza a lista "todosOsProdutos" já carregada pelo dashboard.js
// (mesma origem de dados usada na coluna "Situação" da tabela).

function obterItensParaReposicao() {
    if (typeof todosOsProdutos === "undefined" || !Array.isArray(todosOsProdutos)) {
        return [];
    }
    return todosOsProdutos.filter(item => item.quantidade <= item.estoqueMinimo);
}

function montarLinhasTabelaSolicitacao(itens) {
    const corpo = document.getElementById("corpoTabelaSolicitacao");
    corpo.innerHTML = "";

    itens.forEach(item => {
        const sugestao = Math.max(item.estoqueMinimo * 2 - item.quantidade, item.estoqueMinimo);

        const linha = `
            <tr>
                <td>${item.codigoBarras}</td>
                <td>${item.nomeProduto}</td>
                <td>${item.prateleira || "-"}</td>
                <td>${item.quantidade}</td>
                <td>${item.estoqueMinimo}</td>
                <td><strong>${sugestao}</strong></td>
            </tr>
        `;
        corpo.innerHTML += linha;
    });
}

function gerarTextoSolicitacao(itens, dataFormatada) {
    let texto = "SOLICITACAO DE COMPRA / REPOSICAO DE ESTOQUE\n";
    texto += "Gerado em: " + dataFormatada + "\n";
    texto += "=".repeat(60) + "\n\n";

    if (itens.length === 0) {
        texto += "Nenhum item precisa de reposicao no momento.\n";
        return texto;
    }

    itens.forEach((item, index) => {
        const sugestao = Math.max(item.estoqueMinimo * 2 - item.quantidade, item.estoqueMinimo);
        texto += `${index + 1}. Produto: ${item.nomeProduto}\n`;
        texto += `   Codigo de Barras: ${item.codigoBarras}\n`;
        texto += `   Prateleira: ${item.prateleira || "-"}\n`;
        texto += `   Quantidade atual: ${item.quantidade}\n`;
        texto += `   Estoque minimo: ${item.estoqueMinimo}\n`;
        texto += `   Quantidade sugerida para compra: ${sugestao}\n\n`;
    });

    texto += "=".repeat(60) + "\n";
    texto += `Total de itens a repor: ${itens.length}\n`;

    return texto;
}

function abrirModalSolicitacao() {
    const itens = obterItensParaReposicao();
    const agora = new Date();
    const dataFormatada = agora.toLocaleString("pt-BR");

    document.getElementById("modalDataGeracao").textContent = "Gerado em: " + dataFormatada;

    const semItens = document.getElementById("modalSemItens");
    const tabelaWrapper = document.getElementById("modalTabelaWrapper");

    if (itens.length === 0) {
        semItens.style.display = "block";
        tabelaWrapper.style.display = "none";
    } else {
        semItens.style.display = "none";
        tabelaWrapper.style.display = "block";
        montarLinhasTabelaSolicitacao(itens);
    }

    document.getElementById("modalSolicitacao").dataset.textoExportacao =
        gerarTextoSolicitacao(itens, dataFormatada);

    document.getElementById("modalSolicitacao").style.display = "flex";
}

function fecharModalSolicitacao() {
    document.getElementById("modalSolicitacao").style.display = "none";
}

document.getElementById("btnGerarSolicitacao").addEventListener("click", abrirModalSolicitacao);
document.getElementById("fecharModal").addEventListener("click", fecharModalSolicitacao);

document.getElementById("btnBaixarSolicitacao").addEventListener("click", function () {
    const texto = document.getElementById("modalSolicitacao").dataset.textoExportacao || "";
    const blob = new Blob([texto], { type: "text/plain;charset=utf-8" });
    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = "solicitacao-compra-" + Date.now() + ".txt";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
});

document.getElementById("btnImprimirSolicitacao").addEventListener("click", function () {
    window.print();
});
