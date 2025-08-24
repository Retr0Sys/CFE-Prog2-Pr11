package Ejercicio3;

import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SQL_INSERT =
            "INSERT INTO usuarios (nombre, apellido, email, username, password, cedula, fecha_nacimiento, telefono) " +
                    "VALUES (?,?,?,?,?,?,?,?)";

    @Override
    public long guardar(Usuario usuario, Connection conn) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, apellido, email, username, password, cedula, fecha_nacimiento, telefono) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getUsername());
            stmt.setString(5, usuario.getPassword());
            stmt.setString(6, usuario.getCedula());
            stmt.setDate(7, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
            stmt.setString(8, usuario.getTelefono());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return -1;
    }
}
