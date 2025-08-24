package Ejercicio3;

import java.sql.Connection;
import java.sql.SQLException;

public interface UsuarioDAO {
    long guardar(Usuario u, Connection conn) throws SQLException;
}

