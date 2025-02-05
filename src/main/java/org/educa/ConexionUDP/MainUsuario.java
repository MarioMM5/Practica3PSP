package org.educa.ConexionUDP;

import org.educa.ConexionUDP.entity.UsuarioEntity;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;


public class MainUsuario {
    private static LogueoUsuario logueoUsuario;
    private static ChatUsuario chat;
    private static UsuarioEntity usuario;
    private static String historialChat;
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
        while (logueoUsuario.isActive()){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logueoUsuario.dispose();
        System.out.println("Bienvenido " + usuario.getNombreUsuario());
        //TODO: Abrir chat de usuario
        chat = new ChatUsuario(clientSocket, usuario.getNombreUsuario());
        chat.setContentPane(chat.getPanelChat());
        chat.setTitle("Chat de usuario");
        chat.setSize(400, 200);
        chat.setVisible(true);
        chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chat.getTextPane1().setText(historialChat);
        chat.getTextPane1().setText(chat.getTextPane1().getText() + "\n");
        while(true){
            String mensaje = recibirMensajeAlChat(clientSocket);
            if (!mensaje.isEmpty()){
                chat.getTextPane1().setText(chat.getTextPane1().getText() + mensaje + "\n");
            }
        }
    }

    private static String recibirMensajeAlChat(DatagramSocket clientSocket) {
        String mensajeServidor = recibirMensajeDelServidor(clientSocket);
        if (mensajeServidor.split(":")[0].equals("2")){
            String mensajeDepurado = mensajeServidor.split(":")[1] + " : " + mensajeServidor.split(":")[2];
            return mensajeDepurado;
        }
        return "";
    }

    private static String recibirMensajeDelServidor(DatagramSocket clientSocket) {
        byte[] recibidos = new byte[1024];
        DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
        try {
            clientSocket.receive(recibo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mensajeServidor = new String(recibo.getData()).trim();
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

    public static void setNombreUsuario(String nombreUsuario) {
        usuario.setNombreUsuario(nombreUsuario);
    }

    public static void setChat(String chat) {
        historialChat = chat;
    }
}


