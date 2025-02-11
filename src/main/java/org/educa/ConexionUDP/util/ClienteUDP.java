package org.educa.ConexionUDP.util;



import org.educa.ConexionUDP.datos.ChatUDP;

import java.io.IOException;
import java.net.DatagramSocket;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket cliente = new DatagramSocket();
            ChatUDP ventanaChatUDP = new ChatUDP(cliente); // Lanzo una nueva ventana con el chat
        } catch (IOException e) {
            System.out.println("Debes iniciar antes el servidor para abrir el chat");
        }
    }
}