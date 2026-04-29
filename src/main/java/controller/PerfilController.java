package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/api/perfil")
public class PerfilController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // CORRIGIDO: session poderia ser null se o usuário não estivesse logado,
        // causando NullPointerException ao chamar session.getAttribute("perfil").
        // Agora verificamos se a sessão existe antes de usá-la.
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"perfil\":null}");
            return;
        }

        String perfil = (String) session.getAttribute("perfil");

        response.setContentType("application/json");
        response.getWriter().write("{\"perfil\":\"" + perfil + "\"}");
    }
}
