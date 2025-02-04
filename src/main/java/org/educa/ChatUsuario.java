package org.educa;

import lombok.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
public class ChatUsuario extends JFrame{
    private JTextArea chat;
    private JTextField textFieldChat;
    private JButton botonEnviar;
    private JPanel PanelChat;
    private String nombreUsuario;
    private DatagramSocket clientSocket;

    public ChatUsuario(String nombreUsuario, DatagramSocket clientSocket) {
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mensaje = textFieldChat.getText();
                if (mensaje.isEmpty()){
                    JOptionPane.showMessageDialog(null, "No puedes enviar mensajes vacios");
                }else{
                    String mensajeAEnviar = "2:" + nombreUsuario + ":" + mensaje;
                    try {
                        MainUsuario.mandarMensajeAlServidor(mensajeAEnviar, InetAddress.getLocalHost(), clientSocket);
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    }
                    textFieldChat.setText("");
                }
            }
        });
    }


}
