// -------------------------------------------------------
// Bloqueia letras e símbolos no campo Telefone
// Permite apenas dígitos, parênteses, hífen e espaço
// -------------------------------------------------------
const campoTelefone = document.getElementById("telefone");

campoTelefone.addEventListener("input", function () {
    // Remove tudo que não for dígito, parêntese, hífen ou espaço
    let valor = this.value.replace(/[^0-9()\-\s]/g, "");
    this.value = valor;
});

campoTelefone.addEventListener("keydown", function (e) {
    const permitidos = ["Backspace", "Delete", "Tab", "ArrowLeft", "ArrowRight", "Home", "End"];
    const isDigito = e.key >= "0" && e.key <= "9";
    const isEspecial = ["(", ")", "-", " "].includes(e.key);

    if (!isDigito && !isEspecial && !permitidos.includes(e.key)) {
        e.preventDefault();
    }
});

// -------------------------------------------------------
// Bloqueia símbolos e números no campo Nome e Sobrenome
// Permite apenas letras e espaços
// -------------------------------------------------------
function bloqueioSomenteLetras(inputEl) {
    inputEl.addEventListener("input", function () {
        this.value = this.value.replace(/[^a-zA-ZÀ-ÿ\s]/g, "");
    });
    inputEl.addEventListener("keydown", function (e) {
        const permitidos = ["Backspace", "Delete", "Tab", "ArrowLeft", "ArrowRight", "Home", "End", " "];
        const isLetra = /^[a-zA-ZÀ-ÿ]$/.test(e.key);
        if (!isLetra && !permitidos.includes(e.key)) {
            e.preventDefault();
        }
    });
}

bloqueioSomenteLetras(document.getElementById("nameFirst"));
bloqueioSomenteLetras(document.getElementById("sobreNome"));

// -------------------------------------------------------
// Bloqueia símbolos e números no campo Função
// -------------------------------------------------------
bloqueioSomenteLetras(document.getElementById("funcao"));
