// Calcula total automaticamente
document.getElementById("valor").addEventListener("input", calcular);
document.getElementById("quantidade").addEventListener("input", calcular);

function calcular() {
    let valor = parseFloat(document.getElementById("valor").value) || 0;
    let quantidade = parseInt(document.getElementById("quantidade").value) || 0;
    document.getElementById("total").value = (valor * quantidade).toFixed(2);
}

// -------------------------------------------------------
// Bloqueia data de fabricação anterior a 1 ano atrás
// -------------------------------------------------------
const inputFabricacao = document.getElementById("dataFabricacao");
const inputVencimento = document.getElementById("dataVencimento");
const nomeProduto = document.getElementById("nomeProduto");

// Define data mínima de fabricação: 1 ano atrás
function setMinFabricacao() {
    const hoje = new Date();
    const minDate = new Date(hoje.getFullYear() - 1, hoje.getMonth(), hoje.getDate());
    const minStr = minDate.toISOString().split("T")[0];
    inputFabricacao.min = minStr;
    inputFabricacao.max = hoje.toISOString().split("T")[0];
}
setMinFabricacao();

inputFabricacao.addEventListener("change", function () {
    const hoje = new Date();
    const minDate = new Date(hoje.getFullYear() - 1, hoje.getMonth(), hoje.getDate());
    const escolhida = new Date(this.value + "T00:00:00");

    if (escolhida < minDate) {
        alert("❌ Data de fabricação inválida! Não é permitido cadastrar produtos com mais de 1 ano de fabricação.");
        this.value = "";
    }
});

// -------------------------------------------------------
// Bloqueia data de vencimento quando o nome tiver "embalagem"
// -------------------------------------------------------
function verificarBloqueioVencimento() {
    const nome = nomeProduto.value.toLowerCase();
    const isEmbalagem = nome.includes("embalagem");

    if (isEmbalagem) {
        inputVencimento.disabled = true;
        inputVencimento.value = "";
        inputVencimento.title = "Data de vencimento não aplicável para embalagens";
        inputVencimento.style.backgroundColor = "#e0e0e0";
        inputVencimento.removeAttribute("required");
    } else {
        inputVencimento.disabled = false;
        inputVencimento.title = "";
        inputVencimento.style.backgroundColor = "";
        inputVencimento.setAttribute("required", "required");
    }
}

nomeProduto.addEventListener("input", verificarBloqueioVencimento);

// -------------------------------------------------------
// Bloqueia data de vencimento anterior à data de fabricação
// -------------------------------------------------------
function verificarVencimentoAposFabricacao() {
    if (!inputFabricacao.value || !inputVencimento.value) {
        return true;
    }

    const fabricacao = new Date(inputFabricacao.value + "T00:00:00");
    const vencimento = new Date(inputVencimento.value + "T00:00:00");

    if (vencimento < fabricacao) {
        alert("❌ Data de vencimento inválida! Ela não pode ser anterior à data de fabricação.");
        inputVencimento.value = "";
        return false;
    }
    return true;
}

// Sempre que a fabricação mudar, atualiza a data mínima permitida de vencimento
inputFabricacao.addEventListener("change", function () {
    if (this.value) {
        inputVencimento.min = this.value;
    }
    verificarVencimentoAposFabricacao();
});

inputVencimento.addEventListener("change", verificarVencimentoAposFabricacao);

// Garante que o formulário não seja enviado com datas inconsistentes
const formCadastro = document.getElementById("formCadastro");
if (formCadastro) {
    formCadastro.addEventListener("submit", function (event) {
        if (!verificarVencimentoAposFabricacao()) {
            event.preventDefault();
        }
    });
}
