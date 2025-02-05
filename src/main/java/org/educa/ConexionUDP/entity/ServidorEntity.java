package org.educa.ConexionUDP.entity;

import java.net.DatagramSocket;

public class ServidorEntity {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[1024];
    private String chatGeneral="";

    public ServidorEntity(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void recibirMensajeGeneral() {




    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public String getChatGeneral() {
        return chatGeneral;
    }

    public void setChatGeneral(String chatGeneral) {
        this.chatGeneral = chatGeneral;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
