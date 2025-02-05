package org.educa.ConexionTCP.util;

import org.educa.ConexionTCP.datos.ServerTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(12345);
            System.out.println("Servidor TCP iniciado en el puerto " + server.getLocalPort());

            while (true) {
                Socket cliente = server.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                // Por cada usuario del chat, un hilo nuevo
                Thread t = new Thread(new ServerTCP(cliente));
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}