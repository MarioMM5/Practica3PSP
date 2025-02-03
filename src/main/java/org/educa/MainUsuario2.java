package org.educa;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class MainUsuario2 {

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


        try {
            clientSocket.receive(recibo);
        } catch (IOException e) {

            e.printStackTrace();
        }

        clientSocket.close();


    }

}
