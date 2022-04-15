package cubing.lib.utils.sockets;

import java.io.DataOutputStream;
import java.net.*;

public class UDP {
    public static DatagramSocket socket;

    public UDP(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanSend(String host, int port, String data) {
        try {
            unSafeSend(host, port, data);
        } catch (Exception e) {
        }
    }

    public void unSafeSend(String host, int port, String data) throws Exception {
        socket.send(new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(host), port));
    }

    public void send(String host, int port, String data) {
        try {
            unSafeSend(host, port, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
