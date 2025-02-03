package org.educa;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class MainServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(12345);
        } catch (
                SocketException e1) {
            e1.printStackTrace();
        }
        boolean desconexion = true;
        ArrayList<String> usuariosConectados = new ArrayList<String>();
        ArrayList<String> direcciones = new ArrayList<String>();
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
            System.out.println(comprobarNombreUsuario(nombreUsuario, usuariosConectados, recibo));
            if(usuariosConectados.size()>=2){
                System.out.println("Chat general iniciado");
                while(usuariosConectados.size()>=2){

                }
            }

        }
    }
    public static String comprobarNombreUsuario(String usuario, ArrayList<String> usuarios, DatagramPacket datagramPacket){
        if (usuarios.contains(usuario)){
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
            usuarios.add(usuario);
            return "Usuario conectado";
        }
    }
}
