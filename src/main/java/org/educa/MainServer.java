package org.educa;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class MainServer {
    private static ArrayList<String> usuariosConectados = new ArrayList<String>();
    private static ArrayList<DatagramPacket> direcciones = new ArrayList<DatagramPacket>();
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(12345);
        } catch (
                SocketException e1) {
            e1.printStackTrace();
        }
        boolean desconexion = true;

        while (desconexion){
            if (usuariosConectados.isEmpty()){
                System.out.println("Servidor esperando conexiones");
            }

            DatagramPacket recibo;

            byte[] buffer = new byte[1024];

            recibo = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(recibo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String nombreUsuario = new String(recibo.getData()).trim();
            System.out.println(comprobarNombreUsuario(nombreUsuario, recibo));
            if(usuariosConectados.size()>=2){
                System.out.println("Chat general iniciado");
                byte[] chatAbierto = new byte[1024];
                chatAbierto = "Chat general abierto".getBytes();
                for (int i = 0; i < usuariosConectados.size(); i++) {
                    DatagramPacket envio = new DatagramPacket(chatAbierto, chatAbierto.length, direcciones.get(i).getAddress(), direcciones.get(i).getPort());
                    try {
                        socket.send(envio);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                while(usuariosConectados.size()>=2){
                    byte[] mensajeRecibido = new byte[1024];
                    DatagramPacket mensaje = new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
                    try {
                        socket.receive(mensaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String mensajeRecibidoString = new String(mensaje.getData()).trim();
                    for (int i = 0; i < usuariosConectados.size(); i++) {
                        String mensajeAEnviar = nombreUsuario + ": " + mensajeRecibidoString;
                        byte[] mensajeReenviar = new byte[1024];
                        DatagramPacket envio = new DatagramPacket(mensajeReenviar, mensajeReenviar.length, direcciones.get(i).getAddress(), direcciones.get(i).getPort());
                        try {
                            socket.send(envio);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }else{
                System.out.println("No hay usuarios suficientes para escribir por el chat general");
            }
        }
    }
    public static String comprobarNombreUsuario(String usuario, DatagramPacket datagramPacket){
        if (usuariosConectados.contains(usuario)){
            byte[] enviados = new byte[1024];
            enviados = "Usuario ya conectado".getBytes();
            DatagramPacket envio = new DatagramPacket(enviados, enviados.length, datagramPacket.getAddress(), datagramPacket.getPort());
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.send(envio);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Usuario ya conectado";
        }
        else{
            usuariosConectados.add(usuario);
            direcciones.add(datagramPacket);
            return "Usuario conectado";
        }
    }
}
