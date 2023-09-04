package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import conexao.ConexBanco;

public class UsuarioRepository {
    private Connection conn;

    public UsuarioRepository() {
        conn = ConexBanco.getConnection();
    }

    public Usuario insereUsuario(Usuario objeto) throws Exception {
        if (objeto.ehNovo()) {
            String sql = "INSERT INTO user(login, senha) VALUES(?, ?);";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, objeto.getUsuario());
            stmt.setString(2, objeto.getSenha());

            stmt.execute();

            conn.commit();
        } else {
            String sql = "UPDATE user SET login=?, senha=? WHERE id = ?;";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, objeto.getUsuario());
            stmt.setString(2, objeto.getSenha());
            stmt.setLong(3, objeto.getId());

            stmt.executeUpdate();

            conn.commit();
        }

        return this.consultarUsuario(objeto.getId());
    }

    public Usuario consultarUsuario(Long id) throws Exception {
        Usuario user01 = new Usuario();

        String sql = "SELECT * FROM user WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);

        ResultSet rst = stmt.executeQuery();

        while (rst.next()) {
            user01.setId(rst.getLong("id"));
            user01.setUsuario(rst.getString("login"));
            user01.setSenha(rst.getString("senha"));
        }

        return user01;
    }

    public boolean verificaUsuario(String usuario) throws Exception {
        String sql = "SELECT COUNT(1) > 0 AS EXISTE FROM user WHERE login = ?;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, usuario);

        ResultSet res = stmt.executeQuery();

        res.next();
        return res.getBoolean("EXISTE");
    }

    public void deletarUsuario(Long userId) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, userId);
        stmt.executeUpdate();
        conn.commit();
    }

    public List<Usuario> consultarUsuarioLista(String nome) throws Exception {
        List<Usuario> listaUsuario = new ArrayList<Usuario>();

        String sql = "SELECT * FROM user WHERE login LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%");
        ResultSet rst = stmt.executeQuery();

        while (rst.next()) {
            Usuario user01 = new Usuario();

            user01.setId(rst.getLong("id"));
            user01.setUsuario(rst.getString("login"));
            user01.setSenha(rst.getString("senha"));

            listaUsuario.add(user01);
        }
        return listaUsuario;
    }

    public Usuario consultarUsuarioID(Long id) throws Exception {
        Usuario user01 = new Usuario();
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);

        ResultSet rst = stmt.executeQuery();

        while (rst.next()) {
            user01.setId(rst.getLong("id"));
            user01.setUsuario(rst.getString("login"));
            user01.setSenha(rst.getString("senha"));
        }
        return user01;
    }
}
