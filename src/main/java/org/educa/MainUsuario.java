package org.educa;

import org.educa.entity.UsuarioEntity;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.exit;


public class MainUsuario {
    private static LogueoUsuario logueoUsuario;
    private static UsuarioEntity usuario;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InetAddress ipServidor = null;
        DatagramSocket clientSocket = null;
        usuario = new UsuarioEntity(clientSocket);
        try {
            ipServidor = InetAddress.getLocalHost();
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        logueoUsuario = new LogueoUsuario(clientSocket);
        logueoUsuario.setContentPane(logueoUsuario.getPanelLogueo());
        logueoUsuario.setTitle("Logueo de usuario");
        logueoUsuario.setSize(400, 200);
        logueoUsuario.setVisible(true);
        logueoUsuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String nombreUsuario = logueoUsuario.getTextFieldNombreUsuario().getText();
        while (logueoUsuario.isActive()){
            try {
                usuario.setNombreUsuario(logueoUsuario.getTextFieldNombreUsuario().getText());
                System.out.println("Nombre de usuario: " + usuario.getNombreUsuario());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        recibirConfirmacionServidor(clientSocket);
        System.out.println("Bienvenido " + usuario.getNombreUsuario());
        //TODO: Abrir chat de usuario
        ChatUsuario chat = new ChatUsuario(nombreUsuario, clientSocket);
        chat.setContentPane(chat.getPanelChat());
        chat.setTitle("Chat de usuario");
        chat.setSize(600,500);
        chat.setVisible(true);
        chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat.getChat().setText("Bienvenido " + usuario.getNombreUsuario() + "\n");

        //DE ESTO SE ENCARGA EL CHAT USUARIO .JAVA
        //TODO: Si pulsa el boton de enviar mensaje, enviarlo al servidor
    }

    private static String recibirMensajeAlChat(DatagramSocket clientSocket) {
        String mensajeServidor = recibirMensajeDelServidor(clientSocket);
        if (mensajeServidor.split(":")[0].equals("2")){
            String mensajeDepurado = mensajeServidor.split(":")[1] + " : " + mensajeServidor.split(":")[2];
            return mensajeDepurado;
        }
        return "";
    }

    private static void recibirConfirmacionServidor(DatagramSocket clientSocket) {
        String confirmacionConexion = recibirMensajeDelServidor(clientSocket);
        if (confirmacionConexion.split(":")[0].equals("1")){
            System.out.println("Conexion realizada con exito");
            usuario.setNombreUsuario(confirmacionConexion.split(":")[2]);
        }else{
            System.out.println("Error en la conexion");
            exit(0);
        }
    }

    private static String recibirMensajeDelServidor(DatagramSocket clientSocket) {
        byte[] recibidos = new byte[1024];
        DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
        try {
            clientSocket.receive(recibo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mensajeServidor= new String(recibo.getData()).trim();
        System.out.println("Mensaje recibido: " + mensajeServidor);
        return mensajeServidor ;
    }

    public static void mandarMensajeAlServidor(String mensaje, InetAddress ipServidor, DatagramSocket datosCliente){
        byte[] envioString = new byte[1024];
        envioString = mensaje.getBytes();
        DatagramPacket envio = new DatagramPacket(envioString, envioString.length, ipServidor, 12345);
        try {
            datosCliente.send(envio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cerrarVentanaLogueo() {
        logueoUsuario.setVisible(false);

    }
}


