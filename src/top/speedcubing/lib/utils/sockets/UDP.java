package top.speedcubing.lib.utils.sockets;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP {
    public DatagramSocket socket;
    public String host;

    public UDP(String host, int port) {
        try {
            socket = new DatagramSocket(port);
            this.host = host;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendClean(int port, String data) {
        try {
            sendUnsafe(port, data);
        } catch (Exception e) {
        }
    }

    public void sendUnsafe(int port, String data) throws Exception {
        socket.send(new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(host), port));
    }

    public void send(int port, String data) {
        try {
            sendUnsafe(port, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
