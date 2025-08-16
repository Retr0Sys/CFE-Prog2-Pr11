package Ejercicio2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Cedula extends JFrame {
    private JPanel ventana;
    private JLabel lblTitulo;
    private JButton btnConfirmar;
    private JTextField txtCedula;
    private JLabel lblExplicar;

    public Cedula() {
        lblTitulo.getText();
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText().trim();
                boolean cedulaValido = validarCedula(cedula);

                // Mostrar mensaje
                if (cedulaValido) {
                    JOptionPane.showMessageDialog(btnConfirmar, "Cédula válida");
                } else {
                    JOptionPane.showMessageDialog(btnConfirmar, "Cédula inválida");
                }
            }
        });

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
    }
    private boolean validarCedula(String cedula) {
        boolean cedulaValido = false;

        if (cedula.length() == 8 && cedula.matches("\\d+")) {
            // Separamos los dígitos
            String dig1 = cedula.substring(0, 1);
            String dig2 = cedula.substring(1, 2);
            String dig3 = cedula.substring(2, 3);
            String dig4 = cedula.substring(3, 4);
            String dig5 = cedula.substring(4, 5);
            String dig6 = cedula.substring(5, 6);
            String dig7 = cedula.substring(6, 7);
            String dig8 = cedula.substring(7, 8);

            // Convertimos los String a Int
            int dig1Convrt = Integer.parseInt(dig1);
            int dig2Convrt = Integer.parseInt(dig2);
            int dig3Convrt = Integer.parseInt(dig3);
            int dig4Convrt = Integer.parseInt(dig4);
            int dig5Convrt = Integer.parseInt(dig5);
            int dig6Convrt = Integer.parseInt(dig6);
            int dig7Convrt = Integer.parseInt(dig7);
            int dig8Convrt = Integer.parseInt(dig8);

            // Multiplicar y sumar los dígitos del 1 al 7
            int sumaMulti = dig1Convrt * 2 + dig2Convrt * 9 + dig3Convrt * 8 +
                    dig4Convrt * 7 + dig5Convrt * 6 + dig6Convrt * 3 + dig7Convrt * 4;

            // Calculo módulo de sumaMulti
            int moduloSumaMulti = sumaMulti % 10;

            // Ajuste en el cálculo de la resta
            int resultadoRestaSumaMulti = (moduloSumaMulti == 0) ? 0 : 10 - moduloSumaMulti;

            // Controlamos si el dig8 coincide con nuestro dígito calculado
            if (dig8Convrt == resultadoRestaSumaMulti) {
                cedulaValido = true;
            } else {
                cedulaValido = false;
            }
        }

        return cedulaValido;
    }
    public static void main(String[] args) {
        Cedula ventanaCedula = new Cedula();
        ventanaCedula.setContentPane(ventanaCedula.ventana);
        ventanaCedula.setBounds(300, 200, 300, 200);
        ventanaCedula.setVisible(true);
        ventanaCedula.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}