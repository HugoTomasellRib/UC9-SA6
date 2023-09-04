package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexao.ConexBanco;
import model.Usuario;

public class LoginRepository {
    private Connection conn;

    public LoginRepository() {
        conn = ConexBanco.getConnection();
    }

    public boolean validarLogin(Usuario usuario01) throws Exception {
        String sql = "SELECT * FROM user WHERE login = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario01.getUsuario());
        stmt.setString(2, usuario01.getSenha());

        ResultSet rst = stmt.executeQuery();
        return rst.next(); // Retorna true se encontrou um registro correspondente, caso contrário, retorna false.
    }
}
