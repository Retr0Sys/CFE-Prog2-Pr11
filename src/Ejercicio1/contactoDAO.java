package Ejercicio1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class contactoDAO {

        private Connection conexion;

        public contactoDAO(Connection conexion) {
            this.conexion = conexion;
        }

        public void agregarContactos(String nombre, String telefono, String email) throws SQLException {
            String sql = "INSERT INTO contacto (nombre, telefono, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, email);
            stmt.executeUpdate();
            stmt.close();
        }

        public void eliminarContactos(int id) throws SQLException {
            String sql = "DELETE FROM contacto WHERE id = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        }

        public void actualizarTelefono(int id, String nuevoTelefono) throws SQLException {
            String sql = "UPDATE contacto SET telefono = ? WHERE id = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nuevoTelefono);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
        }

    public List<Contacto> listarContactos() throws SQLException {
        String sql = "SELECT * FROM contacto";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        System.out.println("===== Lista de Contactos =====");
        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String telefono = rs.getString("telefono");
            String email = rs.getString("email");

            // Mostrar todos los campos
            System.out.println(id + " | " + nombre + " | " + telefono + " | " + email);
        }

        rs.close();
        stmt.close();
        return null;
    }

    public List<Contacto> buscarPorNombre(String patron) {
        List<Contacto> resultados = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, email FROM contacto WHERE nombre LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + patron + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contacto c = new Contacto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                    resultados.add(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por nombre:");
            e.printStackTrace();
        }
        return resultados;
    }

    public List<Contacto> listarContactosOrdenados(boolean asc) {
        List<Contacto> resultados = new ArrayList<>();
        String sqlAsc = "SELECT id, nombre, telefono, email FROM contacto ORDER BY nombre ASC";
        String sqlDesc = "SELECT id, nombre, telefono, email FROM contacto ORDER BY nombre DESC";
        String sql = asc ? sqlAsc : sqlDesc;

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contacto c = new Contacto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                resultados.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ordenado:");
            e.printStackTrace();
        }
        return resultados;
    }



}

class Contacto {
    private int id;
    private String nombre;
    private String telefono;
    private String email;


    public Contacto(int id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }


    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTelefono(){
        return this.telefono;
    }

    public String getEmail(){
        return this.email;
    }
}
