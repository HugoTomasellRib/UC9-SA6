package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import conexao.ConexBanco;

@WebFilter(urlPatterns = {"/admin-p/*"})
public class FiltroAd extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

    private static Connection conn;

    public FiltroAd() {
    }

    public void destroy() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession sessao = req.getSession();

            String usuarioLogado = (String) sessao.getAttribute("usuario");
            String urlAutenticar = req.getServletPath();

            if (usuarioLogado == null && !urlAutenticar.equalsIgnoreCase("/admin-p/ServletOi")) {
                RequestDispatcher redireciona = request.getRequestDispatcher("/login.jsp");
                request.setAttribute("mensagem", "Por favor efetue o login!");
                redireciona.forward(request, response);
                return;
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher redireciona = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("mensagem", e.getMessage());
            redireciona.forward(request, response);
            try {
                conn.rollback();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        conn = ConexBanco.getConnection();
    }
}
