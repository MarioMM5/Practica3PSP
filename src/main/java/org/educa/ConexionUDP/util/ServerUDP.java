package org.educa.ConexionUDP.util;


import org.educa.ConexionUDP.datos.IntermediarioUDP;

import java.net.DatagramSocket;

public class ServerUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket server = new DatagramSocket(12345);
            System.out.println("Servidor UDP iniciado en el puerto " + server.getLocalPort());
            Thread t = new Thread(new IntermediarioUDP(server)); // Por cada usuario del chat, un hilo nuevo
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}