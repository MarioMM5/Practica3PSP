package org.educa;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class MainUsuario1 {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        InetAddress IPservidor = null;
        try {
            IPservidor = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int puerto = 12345;

        System.out.println("Bienvenido al whatsapp 2");
        System.out.print("Escribe tu nombre de usuario:");
        String nombreUsuario = teclado.nextLine();

        byte[] enviados = new byte[1024];
        enviados = nombreUsuario.getBytes();
        DatagramPacket envio = new DatagramPacket(enviados, enviados.length, IPservidor, puerto);
        try {
            clientSocket.send(envio);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] recibidos = new byte[1024];

        DatagramPacket recibo = new DatagramPacket(recibidos, recibidos.length);

        System.out.println("esperando datagrama");
        try {
            clientSocket.receive(recibo);
        } catch (IOException e) {

            e.printStackTrace();
        }
        String confirmacionConexion = new String(recibo.getData()).trim();
        if(confirmacionConexion.equals("Chat general abierto")){
            System.out.println(confirmacionConexion);
            while(true){
                System.out.print("Escribe lo que quieras");
                byte[] mensajeSaliente = new byte[1024];
                mensajeSaliente = nombreUsuario.getBytes();
                DatagramPacket paqueteSaliente = new DatagramPacket(enviados, enviados.length, IPservidor, puerto);
                //TODO: Terminar el chat general de los usuarios
                try {
                    clientSocket.send(envio);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("No hay suficientes usuarios para chat general");
        }

        System.out.println("Vuelve a intentarlo con otro nombre de usuario");

        clientSocket.close();


    }

}
