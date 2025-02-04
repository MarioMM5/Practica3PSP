package org.educa;

import lombok.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.System.exit;

@Data
public class LogueoUsuario extends JFrame{
    private JButton botonAceptar;
    private JTextField textFieldNombreUsuario;
    private JLabel labelNombreUsuario;
    private JPanel panelLogueo;
    private DatagramSocket clientSocket;

    public LogueoUsuario(DatagramSocket clientSocket) {
        botonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = textFieldNombreUsuario.getText();
                if (nombreUsuario.isEmpty()){
                    JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío");
                } else {
                        byte[] envioString = new byte[1024];
                        nombreUsuario = "1:Conectar:" + nombreUsuario;
                        envioString = nombreUsuario.getBytes();
                        DatagramPacket envio = null;
                    try {
                        envio = new DatagramPacket(envioString, envioString.length, InetAddress.getLocalHost(), 12345);
                        clientSocket.send(envio);
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    byte[] recibidos = new byte[1024];
                    DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
                    try {
                        clientSocket.receive(recibo);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String confirmacionConexion = new String(recibo.getData()).trim();
                    if (confirmacionConexion.split(":")[0].equals("1")){
                        JOptionPane.showMessageDialog(null, "Bienvenido " + nombreUsuario);
                        MainUsuario.cerrarVentanaLogueo();
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso");
                        exit(0);
                    }
                }
            }
        });
    }

}
