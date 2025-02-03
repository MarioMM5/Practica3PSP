package org.educa;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainUsuario {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InetAddress ipServidor = null;
        DatagramSocket clientSocket = null;
        try {
            ipServidor = InetAddress.getLocalHost();
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println(menuUsuarioNoLoggeado());
        int opcion = sc.nextInt();
        if(opcion == 1) {
            System.out.print("Escribe tu nombre de usuario:");
            String nombreUsuario = sc.nextLine();
            mandarMensajeAlServidor(nombreUsuario, ipServidor, clientSocket);
            String confirmacion = recibirMensajeDelServidor(clientSocket);
            System.out.println(confirmacion);
            if(confirmacion.equals("Bienvenido a la aplicación")){
                System.out.println(menuUsuarioLoggeado());
                int opcionUsuarioLoggeado = sc.nextInt();
                if(opcionUsuarioLoggeado == 1){
                    System.out.println("Leyendo mensajes...");
                } else if(opcionUsuarioLoggeado == 2){
                    System.out.println("Escribe el mensaje que quieres enviar");
                    String mensaje = sc.nextLine();
                    mandarMensajeAlServidor(mensaje, ipServidor, clientSocket);
                } else if(opcionUsuarioLoggeado == 0){
                    System.out.println("Saliendo...");
                    exit(0);
                }
            }else{
                System.out.println("El nombre de usuario ya está en uso");
                exit(0);
            }
        } else if (opcion == 0) {
            System.out.println("Saliendo...");
            exit(0);
        }

    }

    private static String recibirMensajeDelServidor(DatagramSocket clientSocket) {
        byte[] recibidos = new byte[1024];
        DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);
        System.out.println("esperando datagrama");
        try {
            clientSocket.receive(recibo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String confirmacionConexion = new String(recibo.getData()).trim();
        return confirmacionConexion;
    }

    public static String menuUsuarioLoggeado(){
        return "1. Leer mensajes\n2. Enviar mensajes\n0. Salir";
    }
    public static String menuUsuarioNoLoggeado(){
        return "1. Conectar\n0. Salir";
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

}


