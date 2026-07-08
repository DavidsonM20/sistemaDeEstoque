async function filtroEstoque() {
    try {
        const nome = document.getElementById("pesquisarNome").value;
        const tipo = document.getElementById("tipoMovimentacao").value;
        const data = document.getElementById("filtroData").value;

        const url = `../api/estoque?nome=${encodeURIComponent(nome)}&tipo=${encodeURIComponent(tipo)}&data=${encodeURIComponent(data)}`;
        const response = await fetch(url);
        const dados = await response.json();

        const filtrados = dados.filter(item => {
            const matchNome = nome === "" || item.nomeProduto.toLowerCase().includes(nome.toLowerCase());
            const matchTipo = tipo === "" || item.status === tipo;
            const matchData = data === "" || item.dataVencimento === data;

            return matchNome && matchTipo && matchData;
        });

        // Atualiza a lista global e reinicia a paginação
        todosOsProdutos = filtrados;
        paginaAtual = 1;
        renderizarTabela();
        atualizarPaginacao();

    } catch (erro) {
        console.error("Erro ao filtrar", erro);
    }
}

document.getElementById("btnPesquisar").addEventListener("click", filtroEstoque);
