async function validarLogin() {
    try {
        const res = await fetch("http://localhost:8080/api/perfil");
        const dado = await res.json();

        console.log("PERFIL FRONT: ", dado.perfil);

        if (!dado.perfil || dado.perfil.toLowerCase() !== "admin") {
            // CORRIGIDO 1: getElementsByClassName não usa "." — era ".btn-menu", deve ser "btn-menu".
            // CORRIGIDO 2: getElementsByClassName retorna uma coleção (HTMLCollection), não um elemento único.
            //              É preciso iterar sobre os elementos para aplicar o estilo a cada um.
            const botoes = document.getElementsByClassName("btn-menu");
            for (let i = 0; i < botoes.length; i++) {
                botoes[i].style.display = "none";
            }
        }
    } catch (e) {
        // CORRIGIDO 3: era "console.erro" — o método correto é "console.error".
        console.error("Erro ao verificar o perfil.", e);
    }
}

validarLogin();
