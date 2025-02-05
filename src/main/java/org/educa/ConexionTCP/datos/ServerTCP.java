package org.educa.ConexionTCP.datos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTCP extends Thread {
    // Lista de salidas de los clientes
    private static final ArrayList<DataOutputStream> clienteOutputs = new ArrayList<>();
    // Lista de nombres de usuario
    private static final ArrayList<String> usernames = new ArrayList<>();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private String mensaje;

    /**
     * Constructor de la clase ServerTCP
     * @param socket Socket del server
     */
    public ServerTCP(Socket socket) {
        this.socket = socket;
        try {
            // Crear los flujos de entrada y salida
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            synchronized (clienteOutputs) {
                clienteOutputs.add(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                mensaje = in.readUTF();
                if (mensaje.startsWith("USUARIO:")) {
                    username = mensaje.split(":")[1].trim();
                    if (usernames.contains(username)) {
                        out.writeUTF("ERROR: El nombre de usuario ya está en uso.");
                    } else {
                        usernames.add(username);
                        out.writeUTF(username + ", bienvenido al chat!");
                    }
                } else {
                    synchronized (clienteOutputs) {
                        for (DataOutputStream clientOut : clienteOutputs) {
                            clientOut.writeUTF(username + ": " + mensaje);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado: " + socket.getInetAddress());
        }
        try {
            synchronized (clienteOutputs) {
                clienteOutputs.remove(out);
                if (username != null) {
                    // Eliminar el nombre de usuario al desconectar
                    usernames.remove(username);
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}