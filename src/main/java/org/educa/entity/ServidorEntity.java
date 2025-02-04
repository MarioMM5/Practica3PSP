package org.educa.entity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorEntity {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[1024];
    private String chat;

    public ServidorEntity(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public void enviarConfirmacionConexion(String usuario, DatagramPacket datagramPacket) {
        String mensajeConfirmacion = "Bienvenido al programa: "+usuario ;
        byte[] mensaje = mensajeConfirmacion.getBytes();
        DatagramPacket envio = new DatagramPacket(mensajeConfirmacion, mensajeReenviar.length, direcciones.get(i).getAddress(), direcciones.get(i).getPort());
        try {
            socket.send(envio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recibirMensajeGeneral(String nombre, String mensaje) {

    }
    public void enviarChatGeneral(){

    }
}
