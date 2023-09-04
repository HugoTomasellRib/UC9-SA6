package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import dao.LoginRepository;

import java.io.IOException;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginRepository loginRepository = new LoginRepository();

    public ServletLogin() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
            encerrarSessao(request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String url = request.getParameter("url");

        if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {
            Usuario user01 = new Usuario();
            user01.setUsuario(usuario);
            user01.setSenha(senha);

            if (loginRepository.validarLogin(user01)) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", user01.getUsuario());

                if (url == null || url.equals("null")) {
                    url = "/admin-p/inicio.jsp";
                }
                response.sendRedirect(request.getContextPath() + url);
            } else {
                request.setAttribute("mensagem", "Usuário ou Senha incorretos!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("mensagem", "Informe o Usuário e Senha corretamente!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    private void encerrarSessao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
