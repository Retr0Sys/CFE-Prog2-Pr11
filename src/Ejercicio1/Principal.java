package Ejercicio1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner leer = new Scanner(System.in);

        try {
            conn = ConexionBD.obtenerConexion();
            if (conn == null) {
                System.out.println("Error: No se pudo establecer la conexión con la base de datos.");
                return;
            }

            contactoDAO contactoDAO = new contactoDAO(conn);

            System.out.println("---- Opciones disponibles ----");
            System.out.println("1. Agregar contactos");
            System.out.println("2. Actualizar contactos");
            System.out.println("3. Eliminar contactos");
            System.out.println("4. Listar contactos");
            System.out.println("5. Buscar por nombre");
            System.out.println("6. Listar alfabéticamente");
            System.out.print("Ingrese una opción: ");
            int num = leer.nextInt();
            leer.nextLine(); // Limpiar buffer

            if (num == 1) {
                System.out.print("Nombre: ");
                String nombre = leer.nextLine();
                System.out.print("Teléfono: ");
                String telefono = leer.nextLine();
                System.out.print("Email: ");
                String email = leer.nextLine();

                if (nombre.isEmpty() || telefono.isEmpty()) {
                    System.out.println("Error: El nombre y el teléfono no pueden estar vacíos.");
                } else if (!esTelfValid(telefono)) {
                    System.out.println("Error: El teléfono ingresado no es válido. Debe tener entre 8 y 15 dígitos.");
                } else if (!esMailValido(email)) {
                    System.out.println("Error: El email ingresado no es válido.");
                } else {
                    contactoDAO.agregarContactos(nombre, telefono, email);
                    System.out.println("Contacto agregado correctamente.");
                }

            } else if (num == 2) {
                System.out.print("ID del contacto a actualizar: ");
                int idActualizar = leer.nextInt();
                leer.nextLine(); // Limpiar buffer
                System.out.print("Nuevo teléfono: ");
                String nuevoTelefono = leer.nextLine();

                if (nuevoTelefono.isEmpty()) {
                    System.out.println("Error: El teléfono no puede estar vacío.");
                } else if (!esTelfValid(nuevoTelefono)) {
                    System.out.println("Error: El teléfono ingresado no es válido. Debe tener entre 8 y 15 dígitos.");
                } else {
                    contactoDAO.actualizarTelefono(idActualizar, nuevoTelefono);
                    System.out.println("Teléfono actualizado correctamente.");
                }

            } else if (num == 3) {
                System.out.print("ID del contacto a eliminar: ");
                int idEliminar = leer.nextInt();
                contactoDAO.eliminarContactos(idEliminar);
                System.out.println("Contacto eliminado correctamente.");

            } else if (num == 4) {
                contactoDAO.listarContactos();

            }else if(num == 5){
                System.out.print("Ingrese el nombre a buscar: ");
                String nombBuscar = leer.nextLine();
                List<Contacto> resultados = contactoDAO.buscarPorNombre(nombBuscar);

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron contactos con ese nombre.");
                } else {
                    for (Contacto c : resultados) {
                        System.out.println("ID: " + c.getId());
                        System.out.println("Nombre: " + c.getNombre());
                        System.out.println("Teléfono: " + c.getTelefono());
                        System.out.println("Email: " + c.getEmail());
                        System.out.println("---------------------------");
                    }
                }
            }else if (num == 6) {
                System.out.print("¿Orden ascendente (A) o descendente (D)? ");
                String orden = leer.nextLine().trim().toUpperCase();
                boolean asc = orden.equals("A");

                List<Contacto> resultados = contactoDAO.listarContactosOrdenados(asc);

                if (resultados.isEmpty()) {
                    System.out.println("No hay contactos para mostrar.");
                } else {
                    for (Contacto c : resultados) {
                        System.out.println("ID: " + c.getId());
                        System.out.println("Nombre: " + c.getNombre());
                        System.out.println("Teléfono: " + c.getTelefono());
                        System.out.println("Email: " + c.getEmail());
                        System.out.println("---------------------------");
                    }
                }
            } else {
                System.out.println("Opción inválida.");
            }

        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static boolean esTelfValid(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        String regex = "\\d{8,15}";
        return telefono.matches(regex);
    }

    public static boolean esMailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
}