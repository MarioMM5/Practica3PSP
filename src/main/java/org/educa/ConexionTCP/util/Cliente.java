package org.educa.ConexionTCP.util;

import org.educa.ConexionTCP.datos.Chat;

import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 12345);
            Chat ventanaChat = new Chat(cliente); // Lanzo una nueva ventana con el chat
        } catch (IOException e) {
            System.out.println("Servidor no iniciado");
        }
    }
}