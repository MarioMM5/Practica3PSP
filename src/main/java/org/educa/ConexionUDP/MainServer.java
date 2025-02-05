package org.educa.ConexionUDP;

import org.educa.ConexionUDP.entity.ServidorEntity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class MainServer {
    private static ArrayList<String> usuariosConectados = new ArrayList<String>();
    private static ArrayList<DatagramPacket> direcciones = new ArrayList<DatagramPacket>();
    private static ServidorEntity servidorEntity;
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(12345);
        } catch (
                SocketException e1) {
            e1.printStackTrace();
        }
        servidorEntity = new ServidorEntity(socket);
        servidorEntity.setChatGeneral("Bienvenido al chat general \n");

        while(true){
                recibirPaquete(servidorEntity.getDatagramSocket());
            }
        }

    private static void recibirPaquete(DatagramSocket socket) {
        byte[] buffer = new byte[1024];
        DatagramPacket recibo = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(recibo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mensaje = new String(recibo.getData()).trim();
        String prefijo = mensaje.split(":")[0];
        if(prefijo.contains("1")){
            System.out.println("comprobando nombre");
            comprobarNombreUsuario(mensaje.split(":")[2], recibo);
        } else if (prefijo.contains("2")) {
            recibirMensaje(mensaje,recibo);
        }
    }

    private static void recibirMensaje(String mensaje, DatagramPacket recibo) {
        servidorEntity.setChatGeneral(servidorEntity.getChatGeneral() + mensaje.split(":")[1] + " : " + mensaje.split(":")[2] + "\n");
        System.out.println(servidorEntity.getChatGeneral());
        String nombreUsuario = mensaje.split(":")[1];
        String mensajeUsuario = mensaje.split(":")[2];
        for (DatagramPacket direccion : direcciones){
            broadcastMensaje(nombreUsuario, mensajeUsuario, direccion);
        }

    }

    private static void broadcastMensaje(String nombreUsuario, String mensajeUsuario, DatagramPacket direccion) {
        String mensaje = "2:" + nombreUsuario + ":" + mensajeUsuario;
        byte[] enviados = mensaje.getBytes();
        DatagramPacket envio = new DatagramPacket(enviados, enviados.length, direccion.getAddress(), direccion.getPort());
        try{
            servidorEntity.getDatagramSocket().send(envio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void comprobarNombreUsuario(String usuario, DatagramPacket datagramPacket){
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
        }else{
            String confirmacion = "1:Nombre valido:"+usuario+":"+servidorEntity.getChatGeneral();
            System.out.println(usuario);
            byte[] enviados = confirmacion.getBytes();
            DatagramPacket envio = new DatagramPacket(enviados, enviados.length, datagramPacket.getAddress(), datagramPacket.getPort());
            try {
                DatagramSocket socket = new DatagramSocket();
                socket.send(envio);
            } catch (IOException e) {
                e.printStackTrace();
            }
            usuariosConectados.add(usuario);
            direcciones.add(datagramPacket);
        }
    }
}
