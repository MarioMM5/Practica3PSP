package org.educa.entity;

import java.net.DatagramSocket;

public class ServidorEntity {

    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[1024];

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

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
