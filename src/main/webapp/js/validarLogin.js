async function validarLogin() {
    try {
        const res = await fetch("http://localhost:8080/api/perfil");
        const dado = await res.json();

        console.log("PERFIL FRONT: ", dado.perfil);

        if (!dado.perfil || dado.perfil.toLowerCase() !== "admin") {
            // getElementsByClassName retorna uma coleção - precisa iterar
            // e NÃO usa ponto no nome da classe
            const botoes = document.getElementsByClassName("btn-menu");
            for (let i = 0; i < botoes.length; i++) {
                botoes[i].style.display = "none";
            }
        }
    } catch (e) {
        console.error("Erro ao verificar o perfil.", e);
    }
}

validarLogin();
