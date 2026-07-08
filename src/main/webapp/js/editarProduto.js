const modalEditar = document.getElementById("modalEditar");
const formEditarProduto = document.getElementById("formEditarProduto");
const mensagemEditar = document.getElementById("mensagemEditar");

// -------------------------------------------------------
// Abrir modal (clique no botão "editar" da tabela)
// -------------------------------------------------------
document.getElementById("corpoTabela").addEventListener("click", function (event) {
    const btnEditar = event.target.closest(".btn-editar");
    const btnExcluir = event.target.closest(".btn-excluir");

    if (btnEditar) {
        abrirModalEdicao(btnEditar.dataset.id);
    }

    if (btnExcluir) {
        excluirProduto(btnExcluir.dataset.id, btnExcluir.dataset.nome);
    }
});

function abrirModalEdicao(id) {
    const produto = todosOsProdutos.find(p => String(p.id) === String(id));

    if (!produto) {
        alert("Produto não encontrado.");
        return;
    }

    exibirMensagemEditar("", false);

    document.getElementById("editId").value = produto.id;
    document.getElementById("editCodigoBarras").value = produto.codigoBarras || "";
    document.getElementById("editNomeProduto").value = produto.nomeProduto || "";
    document.getElementById("editFabricante").value = produto.fabricante || "";
    document.getElementById("editMarca").value = produto.marca || "";
    document.getElementById("editDataFabricacao").value = produto.dataFabricacao || "";
    document.getElementById("editDataVencimento").value = produto.dataVencimento || "";
    document.getElementById("editQuantidade").value = produto.quantidade || 0;
    document.getElementById("editValor").value = parseFloat(produto.valor) || 0;
    document.getElementById("editTotal").value = parseFloat(produto.total) || 0;
    document.getElementById("editStatus").value = produto.status || "";
    document.getElementById("editPrateleira").value = produto.prateleira || "";
    document.getElementById("editEstoqueMinimo").value = produto.estoqueMinimo || 5;

    modalEditar.style.display = "flex";
}

function fecharModalEdicao() {
    modalEditar.style.display = "none";
    exibirMensagemEditar("", false);
}

document.getElementById("fecharModalEditar").addEventListener("click", fecharModalEdicao);
document.getElementById("btnCancelarEdicao").addEventListener("click", fecharModalEdicao);

modalEditar.addEventListener("click", function (event) {
    if (event.target === modalEditar) {
        fecharModalEdicao();
    }
});

// -------------------------------------------------------
// Calcula total automaticamente dentro do modal
// -------------------------------------------------------
document.getElementById("editValor").addEventListener("input", calcularTotalEdicao);
document.getElementById("editQuantidade").addEventListener("input", calcularTotalEdicao);

function calcularTotalEdicao() {
    const valor = parseFloat(document.getElementById("editValor").value) || 0;
    const quantidade = parseInt(document.getElementById("editQuantidade").value) || 0;
    document.getElementById("editTotal").value = (valor * quantidade).toFixed(2);
}

// -------------------------------------------------------
// Validação: vencimento não pode ser anterior à fabricação
// -------------------------------------------------------
function verificarDatasEdicao() {
    const fabricacaoStr = document.getElementById("editDataFabricacao").value;
    const vencimentoStr = document.getElementById("editDataVencimento").value;

    if (!fabricacaoStr || !vencimentoStr) {
        return true;
    }

    const fabricacao = new Date(fabricacaoStr + "T00:00:00");
    const vencimento = new Date(vencimentoStr + "T00:00:00");

    if (vencimento < fabricacao) {
        exibirMensagemEditar("❌ Data de vencimento não pode ser anterior à data de fabricação.", true);
        return false;
    }
    return true;
}

// -------------------------------------------------------
// Salvar edição (envia via fetch, sem recarregar a página)
// -------------------------------------------------------
formEditarProduto.addEventListener("submit", async function (event) {
    event.preventDefault();

    if (!verificarDatasEdicao()) {
        return;
    }

    const formData = new FormData(formEditarProduto);

    try {
        const response = await fetch("../api/produto/atualizar", {
            method: "POST",
            body: formData
        });

        const resultado = await response.json();

        if (resultado.success) {
            exibirMensagemEditar("✔ " + resultado.message, false);
            await carregarEstoque();
            setTimeout(fecharModalEdicao, 700);
        } else {
            exibirMensagemEditar("❌ " + resultado.message, true);
        }
    } catch (erro) {
        console.error("Erro ao atualizar produto:", erro);
        exibirMensagemEditar("❌ Erro de comunicação com o servidor.", true);
    }
});

function exibirMensagemEditar(texto, isErro) {
    if (!texto) {
        mensagemEditar.style.display = "none";
        return;
    }
    mensagemEditar.textContent = texto;
    mensagemEditar.style.display = "block";
    mensagemEditar.style.color = isErro ? "#e74c3c" : "#1b9c85";
}

// -------------------------------------------------------
// Excluir produto
// -------------------------------------------------------
async function excluirProduto(id, nome) {
    const confirmar = confirm(`Tem certeza que deseja excluir o produto "${nome}"? Essa ação não pode ser desfeita.`);
    if (!confirmar) {
        return;
    }

    try {
        const formData = new FormData();
        formData.append("id", id);

        const response = await fetch("../api/produto/excluir", {
            method: "POST",
            body: formData
        });

        const resultado = await response.json();

        if (resultado.success) {
            await carregarEstoque();
        } else {
            alert("❌ " + resultado.message);
        }
    } catch (erro) {
        console.error("Erro ao excluir produto:", erro);
        alert("❌ Erro de comunicação com o servidor.");
    }
}
