package Ejercicio3;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class FormRegist extends JFrame {
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JLabel lblUsuario;
    private JLabel lblContra;
    private JLabel lblConfContra;
    private JLabel lblcedulaUY;
    private JLabel lblFechNac;
    private JLabel lblTelf;
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtApellido;
    private JTextField txtUsuario;
    private JPasswordField pssContra;
    private JPasswordField pssConfContra;
    private JTextField txtCedula;
    private JSpinner spAnio;
    private JTextField txtTelf;
    private JButton btnRegistrar;
    private JButton btnLimpiar;
    private JLabel lblExplicarNomb;
    private JLabel lblExplicarApl;
    private JLabel lblExplicarEmail;
    private JLabel lblExplicarUsuario;
    private JLabel lblExplicarContra;
    private JLabel lblExplicarConfContra;
    private JLabel lblExplicarCedula;
    private JLabel lblExplicarFecha;
    private JLabel lblExplicarTelf;
    private JSpinner spMes;
    private JSpinner spDia;
    private JPanel contenedor;
    private JPanel ventana = new JPanel();
    //Conseguimos la fecha actual
    LocalDate hoy = LocalDate.now();



    public FormRegist() {
        ventana.setLayout(new FlowLayout());
        ventana.add(lblTitulo);
        btnRegistrar.setEnabled(false);
        //variables que guardan por separado la fecha
        spDia.setValue(hoy.getDayOfMonth());
        spMes.setValue(hoy.getMonthValue());
        spAnio.setValue(hoy.getYear());
        //Verificamos la entrada de nombre
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(txtNombre.getText().isEmpty()){
                    txtNombre.setBackground(Color.red);
                    lblExplicarNomb.setText("No campo vacío");
                }
                if(contieneNumeroNomb()){
                    txtNombre.setBackground(Color.red);
                    lblExplicarNomb.setText("No se admiten números en el nombre");
                }
                if(contieneCaracteresEspecialesNomb()){
                    txtNombre.setBackground(Color.red);
                    lblExplicarNomb.setText("No se admiten caracteres especiales en el nombre");
                }
                if(!contieneNumeroNomb() && !contieneCaracteresEspecialesNomb() && !txtNombre.getText().isEmpty()){
                    txtNombre.setBackground(Color.green);
                    lblExplicarNomb.setText("Nombre válido");
                    actualizarEstadoRegistrar();
                }
            }
        });
        //Verificamos la entrada de apellido
        txtApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(txtApellido.getText().isEmpty()){
                    txtApellido.setBackground(Color.red);
                    lblExplicarApl.setText("No campo vacío");
                }
                if(contieneNumeroApl()){
                    txtApellido.setBackground(Color.red);
                    lblExplicarApl.setText("No se admiten números en el apellido");
                }
                if(contieneCaracteresEspecialesApl()){
                    txtApellido.setBackground(Color.red);
                    lblExplicarApl.setText("No se admiten caracteres especiales en el apellido");
                }
                if(!contieneNumeroApl() && !contieneCaracteresEspecialesApl() && !txtApellido.getText().isEmpty()){
                    txtApellido.setBackground(Color.green);
                    lblExplicarApl.setText("Apellido válido");
                    actualizarEstadoRegistrar();
                }
            }
        });
        //Verificamos la entrada de email
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(!validarEmail()){
                    txtEmail.setBackground(Color.red);
                    lblExplicarEmail.setText("Email inválido");
                }if(validarEmail()){
                    txtEmail.setBackground(Color.green);
                    lblExplicarEmail.setText("Email válido");
                    actualizarEstadoRegistrar();
                } else{
                    txtEmail.setBackground(Color.white);
                    lblExplicarEmail.setText("");
                }
            }
        });
        //Verificamos la entrada de usuario
        txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(txtUsuario.getText().isEmpty()){
                    txtUsuario.setBackground(Color.red);
                    lblExplicarUsuario.setText("No campo vacío");
                }
                if(txtUsuario.getText().length()<6){
                    txtUsuario.setBackground(Color.orange);
                    lblExplicarUsuario.setText("Mínimo 5 caracteres");
                }else{
                    txtUsuario.setBackground(Color.green);
                    lblExplicarUsuario.setText("Usuario válido");
                    actualizarEstadoRegistrar();
                }
            }
        });
        //Verificamos la entrada de contraseña y confirmación
        pssContra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!esLongitudValidaContra1()) {
                    pssContra.setBackground(Color.yellow);
                    lblExplicarContra.setText("La contraseña debe de tener al menos 8 caracteres");
                    lblExplicarConfContra.setText("");
                } else if (!contieneNumero()) {
                    pssContra.setBackground(Color.red);
                    lblExplicarContra.setText("La contraseña debe de tener al menos un número");
                    lblExplicarConfContra.setText("");
                } else if (esLongitudValidaContra2() && contieneNumero() && !validarConfirmacion()) {
                    pssContra.setBackground(Color.orange);
                    lblExplicarConfContra.setText("Las contraseñas no coinciden");
                } else if (validarContrasenia() && validarConfirmacion()) {
                    pssContra.setBackground(Color.green);
                    lblExplicarContra.setText("Contraseña correcta");
                    lblExplicarConfContra.setText("Contraseña correcta");
                } else {
                    pssContra.setBackground(Color.white);
                    lblExplicarContra.setText("");
                    lblExplicarConfContra.setText("");
                }
                actualizarEstadoRegistrar();
            }
        });
        pssConfContra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!esLongitudValidaContra2()) {
                    pssConfContra.setBackground(Color.yellow);
                    lblExplicarConfContra.setText("La contraseña debe de tener al menos 8 caracteres");
                } else if (!contieneNumero()) {
                    pssConfContra.setBackground(Color.red);
                    lblExplicarConfContra.setText("La contraseña debe de tener al menos un número");
                } else if (!validarConfirmacion()) {
                    pssConfContra.setBackground(Color.orange);
                    lblExplicarConfContra.setText("Las contraseñas no coinciden");
                } else if (validarContrasenia() && validarConfirmacion()) {
                    pssConfContra.setBackground(Color.green);
                    lblExplicarConfContra.setText("Contraseña correcta");
                } else {
                    pssConfContra.setBackground(Color.white);
                    lblExplicarConfContra.setText("");
                }
                actualizarEstadoRegistrar();
            }
        });
        //Verificamos la entrada de teléfono
        txtTelf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(txtTelf.getText().isEmpty()){
                    txtTelf.setBackground(Color.red);
                    lblExplicarTelf.setText("No campo vacío");
                }
                if(!validarTelf()){
                    txtTelf.setBackground(Color.red);
                    lblExplicarTelf.setText("Telefono inválido");
                }
                if(validarTelf()){
                    txtTelf.setBackground(Color.green);
                    lblExplicarTelf.setText("Teléfono válido");
                    actualizarEstadoRegistrar();
                }
            }
        });
        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                if(txtCedula.getText().isEmpty()){
                    txtCedula.setBackground(Color.red);
                    lblExplicarCedula.setText("No campo vacío");
                }if(!validarCedula(txtCedula.getText())){
                    txtCedula.setBackground(Color.red);
                    lblExplicarCedula.setText("Cédula inválida");
                }if(validarCedula(txtCedula.getText())){
                    txtCedula.setBackground(Color.green);
                    lblExplicarCedula.setText("Cédula válida");
                }
            }
        });

        //Acciones del botón de registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                try {
                    conn = ConexionDB.getConnection();
                    if (conn == null) {
                        JOptionPane.showMessageDialog(FormRegist.this, "Error: No se pudo establecer la conexión con la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(FormRegist.this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Usuario u = new Usuario();
                u.setNombre(txtNombre.getText().trim());
                u.setApellido(txtApellido.getText().trim());
                u.setEmail(txtEmail.getText().trim());
                u.setUsername(txtUsuario.getText().trim());
                u.setPassword(new String(pssContra.getPassword()).trim());
                u.setCedula(txtCedula.getText().trim());
                int dia = Integer.parseInt(spDia.getValue().toString());
                int mes = Integer.parseInt(spMes.getValue().toString());
                int anio = Integer.parseInt(spAnio.getValue().toString());
                LocalDate fechaNacimiento = LocalDate.of(anio, mes, dia);
                u.setFechaNacimiento(fechaNacimiento);
                u.setTelefono(txtTelf.getText().trim());
                UsuarioDAO udao = new UsuarioDAOImpl();
                try {
                    long id = udao.guardar(u, conn);
                    System.out.println("Usuario registrado con ID: " + id);
                } catch (SQLException er) {
                    System.out.println("Error de base de datos: " + er.getMessage());
                } catch (Exception er) {
                    System.out.println("Error inesperado: " + er.getMessage());
                }finally {
                    try {
                        if (conn != null && !conn.isClosed()) {
                            conn.close();
                            System.out.println("Conexión cerrada correctamente.");
                        }
                    } catch (SQLException er) {
                        System.out.println("Error al cerrar la conexión: " + er.getMessage());
                    }
                }
                JOptionPane.showMessageDialog(FormRegist.this, "¡Registro exitoso!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //Acciones del botón de limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombre.setText("");
                txtApellido.setText("");
                txtEmail.setText("");
                txtUsuario.setText("");
                pssContra.setText("");
                pssConfContra.setText("");
                txtCedula.setText("");
                txtTelf.setText("");
                spDia.setValue(hoy.getDayOfMonth());
                spMes.setValue(hoy.getMonthValue());
                spAnio.setValue(hoy.getYear());

                txtNombre.setBackground(Color.white);
                txtApellido.setBackground(Color.white);
                txtEmail.setBackground(Color.white);
                txtUsuario.setBackground(Color.white);
                pssContra.setBackground(Color.white);
                pssConfContra.setBackground(Color.white);
                txtCedula.setBackground(Color.white);
                txtTelf.setBackground(Color.white);

                lblExplicarNomb.setText("");
                lblExplicarApl.setText("");
                lblExplicarEmail.setText("");
                lblExplicarUsuario.setText("");
                lblExplicarContra.setText("");
                lblExplicarConfContra.setText("");
                lblExplicarCedula.setText("");
                lblExplicarFecha.setText("");
                lblExplicarTelf.setText("");

                actualizarEstadoRegistrar();
            }
        });
        //Verificamos la entrada de fecha para día
        spDia.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                actualizarEstadoRegistrar();
            }
        });
        //Verificamos la entrada de fecha para mes
        spMes.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                actualizarEstadoRegistrar();
            }
        });
        //Verificamos la entrada de fecha para año
        spAnio.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                validarFecha();
                actualizarEstadoRegistrar();
            }
        });
    }
    //Métodos que realizar los requerimientos / verificaciones
    public boolean contieneNumeroNomb() {
        return txtNombre.getText().matches(".*\\d.*");
    }
    public boolean contieneNumeroApl() {
        return txtApellido.getText().matches(".*\\d.*");
    }
    public boolean contieneCaracteresEspecialesNomb() {
        return txtNombre.getText().matches(".*[^A-Za-zÁÉÍÓÚáéíóúÑñ\\s].*");
    }

    public boolean contieneCaracteresEspecialesApl() {
        return txtApellido.getText().matches(".*[^A-Za-zÁÉÍÓÚáéíóúÑñ\\s].*");
    }

    private boolean validarCedula(String cedula) {
        boolean cedulaValido = false;

        if (cedula.length() == 8 && cedula.matches("\\d+")) {
            // Separar dígitos
            String dig1 = cedula.substring(0, 1);
            String dig2 = cedula.substring(1, 2);
            String dig3 = cedula.substring(2, 3);
            String dig4 = cedula.substring(3, 4);
            String dig5 = cedula.substring(4, 5);
            String dig6 = cedula.substring(5, 6);
            String dig7 = cedula.substring(6, 7);
            String dig8 = cedula.substring(7, 8);

            // Convertir a enteros
            int dig1Convrt = Integer.parseInt(dig1);
            int dig2Convrt = Integer.parseInt(dig2);
            int dig3Convrt = Integer.parseInt(dig3);
            int dig4Convrt = Integer.parseInt(dig4);
            int dig5Convrt = Integer.parseInt(dig5);
            int dig6Convrt = Integer.parseInt(dig6);
            int dig7Convrt = Integer.parseInt(dig7);
            int dig8Convrt = Integer.parseInt(dig8);

            // Calcular suma ponderada
            int sumaMulti = dig1Convrt * 2 + dig2Convrt * 9 + dig3Convrt * 8 +
                    dig4Convrt * 7 + dig5Convrt * 6 + dig6Convrt * 3 + dig7Convrt * 4;

            // Calcular dígito verificador
            int moduloSumaMulti = sumaMulti % 10;
            int resultadoRestaSumaMulti = (moduloSumaMulti == 0) ? 0 : 10 - moduloSumaMulti;

            // Comparar con último dígito
            if (dig8Convrt == resultadoRestaSumaMulti) {
                cedulaValido = true;
            } else {
                cedulaValido = false;
            }
        }

        return cedulaValido;
    }

    public boolean esLongitudValidaContra1() {
        return new String(pssContra.getPassword()).length() >= 8;
    }

    public boolean esLongitudValidaContra2() {
        return new String(pssConfContra.getPassword()).length() >= 8;
    }

    public boolean contieneNumero() {
        return new String(pssContra.getPassword()).matches(".*\\d.*");
    }

    public boolean validarContrasenia() {
        return esLongitudValidaContra1() && contieneNumero();
    }

    public boolean validarConfirmacion() {
        return new String(pssContra.getPassword()).trim().equals(new String(pssConfContra.getPassword()).trim());
    }
    public JPanel getPanel() {
        return ventana;
    }
    //Verificamos la entrada de fecha
    public void validarFecha(){
        int diaSelect = Integer.parseInt(spDia.getValue().toString());
        int mesSelect = Integer.parseInt(spMes.getValue().toString());
        int anioSelect = Integer.parseInt(spAnio.getValue().toString());

        LocalDate fechaSeleccionada = LocalDate.of(anioSelect, mesSelect, diaSelect);
        LocalDate hoy = LocalDate.now();
        LocalDate fechaMinima = hoy.minusYears(18);

        if(fechaSeleccionada.isAfter(hoy)){
            lblExplicarFecha.setText("Fecha inválida");
        } else if(fechaSeleccionada.isAfter(fechaMinima)) {
            lblExplicarFecha.setText("Debes ser mayor de 18 años");
        } else {
            lblExplicarFecha.setText("");
        }
    }

    public boolean validarEmail(){
        return txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

    public boolean validarTelf(){
        return txtTelf.getText().matches("^\\d{8,12}$");
    }
    // Actualizamos el estado del botón de registrar utilizado en el btnRegistrar
    private void actualizarEstadoRegistrar() {
        boolean nombreValido = !txtNombre.getText().isEmpty() && !contieneNumeroNomb() && !contieneCaracteresEspecialesNomb();
        boolean apellidoValido = !txtApellido.getText().isEmpty() && !contieneNumeroApl() && !contieneCaracteresEspecialesApl();
        boolean emailValido = validarEmail();
        boolean usuarioValido = txtUsuario.getText().length() >= 6;
        boolean contraValida = validarContrasenia() && validarConfirmacion();
        boolean cedulaValida = validarCedula(txtCedula.getText());
        int dia = Integer.parseInt(spDia.getValue().toString());
        int mes = Integer.parseInt(spMes.getValue().toString());
        int anio = Integer.parseInt(spAnio.getValue().toString());
        LocalDate fechaSeleccionada = LocalDate.of(anio, mes, dia);
        LocalDate hoy = LocalDate.now();
        LocalDate fechaMinima = hoy.minusYears(18);
        boolean fechaValida = !fechaSeleccionada.isAfter(hoy) && !fechaSeleccionada.isAfter(fechaMinima);
        boolean telfValido = validarTelf();

        boolean todoValido = nombreValido && apellidoValido && emailValido && usuarioValido &&
                contraValida && cedulaValida && fechaValida && telfValido;

        btnRegistrar.setEnabled(todoValido);
    }
    public static void main(String[] args) {
        FormRegist frame = new FormRegist();
        FormRegist ventanaRegistro = new FormRegist();
        frame.setContentPane(ventanaRegistro.contenedor);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

