package cubing.lib.utils.sockets;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP {

    public ServerSocket socket;

    public TCP(int port) {
        try {
            this.socket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanSend(String host, int port, String data, int timeout) {
        try {
            unSafeSend(host, port, data, timeout);
        } catch (Exception e) {
        }
    }

    public void unSafeSend(String host, int port, String data, int timeout) throws Exception {
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port), timeout);
        new DataOutputStream(clientSocket.getOutputStream()).writeBytes(data);
        clientSocket.close();
    }

    public void send(String host, int port, String data, int timeout) {
        try {
            unSafeSend(host, port, data, timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
