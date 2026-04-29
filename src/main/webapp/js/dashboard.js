async function filtroEstoque() {
    try {
        const nomePesquisado = document.getElementById("pesquisarNome").value.toLowerCase();
        const tipoSelecionado = document.getElementById("tipoMovimentacao").value;
        const dataSelecionada = document.getElementById("filtroData").value;

        const response = await fetch("http://localhost:8080/api/estoque");
        const dados = await response.json();

        const tabela = document.getElementById("corpoTabela");
        tabela.innerHTML = "";

        const filtrados = dados.filter(item => {
            const matchNome = nomePesquisado === "" || item.nomeProduto.toLowerCase().includes(nomePesquisado);
            const matchTipo = tipoSelecionado === "" || item.status === tipoSelecionado;
            const matchData = dataSelecionada === "" || item.dataFabricacao === dataSelecionada;

            return matchNome && matchTipo && matchData;
        });

        filtrados.forEach(item => {
            const linha = `
                _
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
                _
            `;
            tabela.innerHTML += linha;
        });

    } catch (erro) {
        console.error("Erro ao filtrar", erro);
    }
}

window.onload = () => {
    carregarEstoque();
    carregarResumo();
};


