package Ejercicio2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Cedula extends JFrame {
    // Componentes de la interfaz
    private JPanel ventana;
    private JLabel lblTitulo;
    private JButton btnConfirmar;
    private JTextField txtCedula;
    private JLabel lblExplicar;
    private JTabbedPane tabbedPane1;
    private JPanel CedulaTabbed;
    private JPanel ContraseniaTabbed;
    private JLabel lblTitulo2;
    private JLabel lblContra;
    private JLabel lblConfirmarContra;
    private JButton btnConfirm2;
    private JTextField txtContra1;
    private JTextField txtContra2;
    private JLabel lblExplicar1;
    private JLabel lblExplicar2;

    public Cedula() {
        // Acciones para el botón de confirmar cédula
        lblTitulo.getText();
        lblTitulo2.getText();
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText().trim();
                boolean cedulaValido = validarCedula(cedula);

                // Mostrar mensaje según validez
                if (cedulaValido) {
                    JOptionPane.showMessageDialog(btnConfirmar, "Cédula válida");
                } else {
                    JOptionPane.showMessageDialog(btnConfirmar, "Cédula inválida");
                }
            }
        });

        // Validación en tiempo real de la cédula
        txtCedula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(txtCedula.getText().length() == 0){
                    txtCedula.setBackground(Color.red);
                    lblExplicar.setText("No campo vacío");
                }else if(txtCedula.getText().length() > 8){
                    txtCedula.setBackground(Color.yellow);
                    lblExplicar.setText("La cédula no puede tener más de 8 dígitos");
                }else if(validarCedula(txtCedula.getText())){
                    lblExplicar.setText("Cédula válida");
                    txtCedula.setBackground(Color.green);
                }else if(!validarCedula(txtCedula.getText())){
                    lblExplicar.setText("Cédula inválida");
                    txtCedula.setBackground(Color.orange);
                }
                else{
                    txtCedula.setBackground(Color.white);
                }
            }
        });

        // Validación en tiempo real del campo de contraseña principal
        txtContra1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(!esLongitudValidaContra1()){
                    txtContra1.setBackground(Color.yellow);
                    lblExplicar1.setText("La contraseña debe de tener al menos 8 caracteres");
                } else if(!contieneNumero()){
                    txtContra1.setBackground(Color.red);
                    lblExplicar1.setText("La contraseña debe de tener al menos un núumero");
                } else if(validarContrasenia() && validarConfirmacion()){
                    txtContra1.setBackground(Color.green);
                    lblExplicar1.setText("Contraseña correcta");
                } else {
                    txtContra1.setBackground(Color.white);
                    lblExplicar1.setText("");
                }
            }
        });

        // Validación en tiempo real del campo de confirmación de contraseña
        txtContra2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(!esLongitudValidaContra2()){
                    txtContra2.setBackground(Color.yellow);
                    lblExplicar2.setText("La contraseña debe de tener al menos 8 caracteres");
                } else if(!contieneNumero()){
                    txtContra2.setBackground(Color.red);
                    lblExplicar2.setText("La contraseña debe de tener al menos un número");
                } else if(!validarConfirmacion()){
                    txtContra2.setBackground(Color.orange);
                    lblExplicar2.setText("Las contraseñas no coinciden");
                } else if(validarContrasenia() && validarConfirmacion()){
                    txtContra2.setBackground(Color.green);
                    lblExplicar2.setText("Contraseña correcta");
                } else {
                    txtContra2.setBackground(Color.white);
                    lblExplicar2.setText("");
                }
            }
        });

        // Acción del botón de confirmar contraseña
        btnConfirm2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contra = txtContra1.getText().trim();
                String contraConfirm = txtContra2.getText().trim();

                boolean contraseniaValida = validarContrasenia();
                boolean confirmacionValida = validarConfirmacion();

                // Mostrar mensaje según validaciones
                if (!contraseniaValida) {
                    JOptionPane.showMessageDialog(btnConfirm2, "La contraseña debe tener más de 8 caracteres y al menos un número.");
                } else if (!confirmacionValida) {
                    JOptionPane.showMessageDialog(btnConfirm2, "Las contraseñas no coinciden.");
                } else {
                    JOptionPane.showMessageDialog(btnConfirm2, "Contraseña válida y confirmada.");
                }
            }
        });
    }

    // Validación de cédula uruguaya (módulo 10)
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

    // Validación de longitud para txtContra1
    public boolean esLongitudValidaContra1() {
        return txtContra1.getText().length() > 8;
    }

    // Validación de longitud para txtContra2
    public boolean esLongitudValidaContra2() {
        return txtContra2.getText().length() > 8;
    }

    // Verifica si txtContra1 contiene al menos un número
    public boolean contieneNumero() {
        return txtContra1.getText().matches(".*\\d.*");
    }

    // Valida que la contraseña principal cumpla requisitos
    public boolean validarContrasenia() {
        return esLongitudValidaContra1() && contieneNumero();
    }

    // Compara si ambas contraseñas son iguales
    public boolean validarConfirmacion() {
        return txtContra1.getText().trim().equals(txtContra2.getText().trim());
    }

    // Configuración de la ventana principal
    public static void main(String[] args) {
        Cedula ventanaCedula = new Cedula();
        ventanaCedula.setTitle("Validación de Cédula y Contraseña");
        ventanaCedula.setContentPane(ventanaCedula.ventana);
        ventanaCedula.setSize(500, 400); // Espacio suficiente para ambas pestañas
        ventanaCedula.setLocationRelativeTo(null); // Centra la ventana en pantalla
        ventanaCedula.setVisible(true);
        ventanaCedula.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}