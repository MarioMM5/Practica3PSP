package org.educa.ConexionTCP.util;

import org.educa.ConexionTCP.datos.ChatTCP;

import java.io.IOException;
import java.net.Socket;

public class ClienteTCP {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 12345);
            ChatTCP ventanaChat = new ChatTCP(cliente); // Lanzo una nueva ventana con el chat
        } catch (IOException e) {
            System.out.println("Servidor no iniciado");
        }
    }
}