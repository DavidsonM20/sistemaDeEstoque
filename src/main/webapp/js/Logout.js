document.getElementById("btnLogout").addEventListener("click", function (){
    // CORRIGIDO: era "/Logout" (L maiúsculo), mas o @WebServlet no servidor
    // está mapeado como "/logout" (l minúsculo). URLs são case-sensitive.
    window.location.href = "http://localhost:8080/logout";
});
