package org.educa.entity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UsuarioEntity {

    private String nombreUsuario;
    private DatagramPacket datagramPacket;

    public UsuarioEntity(String nombreUsuario, DatagramPacket datagramPacket) {
        this.nombreUsuario = nombreUsuario;
        this.datagramPacket = datagramPacket;
    }

    public UsuarioEntity(DatagramSocket clientSocket) {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }
}
