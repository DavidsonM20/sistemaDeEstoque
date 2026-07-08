const ITENS_POR_PAGINA = 10;
let todosOsProdutos = [];
let paginaAtual = 1;

async function carregarEstoque() {
    try {
        const response = await fetch("../api/estoque");
        todosOsProdutos = await response.json();

        paginaAtual = 1;
        renderizarTabela();
        atualizarPaginacao();
    } catch (erro) {
        console.error("Erro ao carregar os produtos.", erro);
    }
}

function renderizarTabela() {
    const tabela = document.getElementById("corpoTabela");
    tabela.innerHTML = "";

    const inicio = (paginaAtual - 1) * ITENS_POR_PAGINA;
    const fim = inicio + ITENS_POR_PAGINA;
    const itensDaPagina = todosOsProdutos.slice(inicio, fim);

    itensDaPagina.forEach(item => {
        const precisaReposicao = item.quantidade <= item.estoqueMinimo;
        const situacao = precisaReposicao
            ? '<span style="color:red;font-weight:bold;">⚠ Repor</span>'
            : '<span style="color:green;">✔ OK</span>';

        const linha = `
            <tr>
                <td>${item.codigoBarras}</td>
                <td>${item.nomeProduto}</td>
                <td>${item.fabricante}</td>
                <td>${item.marca}</td>
                <td>${item.dataFabricacao}</td>
                <td>${item.dataVencimento}</td>
                <td>${item.quantidade}</td>
                <td>${parseFloat(item.valor).toFixed(2)}</td>
                <td>${parseFloat(item.total).toFixed(2)}</td>
                <td>${item.status}</td>
                <td>${item.prateleira || '-'}</td>
                <td>${situacao}</td>
                <td class="coluna-acoes">
                    <button type="button" class="btn-editar" data-id="${item.id}" title="Editar produto">✏️</button>
                    <button type="button" class="btn-excluir" data-id="${item.id}" data-nome="${item.nomeProduto}" title="Excluir produto">🗑️</button>
                </td>
            </tr>
        `;
        tabela.innerHTML += linha;
    });
}

function atualizarPaginacao() {
    const totalPaginas = Math.max(1, Math.ceil(todosOsProdutos.length / ITENS_POR_PAGINA));
    const select = document.getElementById("pagina");
    select.innerHTML = "";

    for (let i = 1; i <= totalPaginas; i++) {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = i;
        if (i === paginaAtual) option.selected = true;
        select.appendChild(option);
    }
}

document.getElementById("pagina").addEventListener("change", function () {
    paginaAtual = parseInt(this.value);
    renderizarTabela();
});

document.getElementById("btnVoltar").addEventListener("click", function () {
    if (paginaAtual > 1) {
        paginaAtual--;
        document.getElementById("pagina").value = paginaAtual;
        renderizarTabela();
    }
});

document.getElementById("btnProximo").addEventListener("click", function () {
    const totalPaginas = Math.ceil(todosOsProdutos.length / ITENS_POR_PAGINA);
    if (paginaAtual < totalPaginas) {
        paginaAtual++;
        document.getElementById("pagina").value = paginaAtual;
        renderizarTabela();
    }
});

async function carregarResumo() {
    try {
        const response = await fetch("../api/resumo");
        const dados = await response.json();

        document.getElementById("cardEntrada").innerHTML = dados.entrada;
        document.getElementById("cardSaida").innerHTML = dados.saida;
        document.getElementById("cardTotal").innerHTML = dados.total;
        document.getElementById("cardReposicao").innerHTML = dados.precisamReposicao;
    } catch (erro) {
        console.error("Erro na consulta dos dados", erro);
    }
}

window.onload = () => {
    carregarEstoque();
    carregarResumo();
};
