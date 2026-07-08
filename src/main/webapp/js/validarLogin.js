async function validarLogin() {
    try {
        const res = await fetch("../api/perfil");
        const dado = await res.json();

        console.log("PERFIL FRONT: ", dado.perfil);

        const perfil = dado.perfil ? dado.perfil.toLowerCase() : "";

        // Oculta botão de Cadastro de usuários para não-admin
        if (perfil !== "admin") {
            const botoes = document.getElementsByClassName("btn-menu");
            for (let i = 0; i < botoes.length; i++) {
                botoes[i].style.display = "none";
            }
        }

        // Admin limitado: oculta botão de adicionar produto (+)
        if (perfil === "admin_limitado") {
            const btnAdd = document.querySelector(".btn-add");
            if (btnAdd) btnAdd.style.display = "none";
        }

    } catch (e) {
        console.error("Erro ao verificar o perfil.", e);
    }
}

validarLogin();
