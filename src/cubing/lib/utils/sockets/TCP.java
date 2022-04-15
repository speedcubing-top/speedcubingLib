package cubing.lib.utils.sockets;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP {

    public ServerSocket socket;
    public String host;
    public int timeout;

    public TCP(String host, int port, int timeout) {
        try {
            this.socket = new ServerSocket(port);
            this.host = host;
            this.timeout = timeout;
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
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port), timeout);
        new DataOutputStream(clientSocket.getOutputStream()).writeBytes(data);
        clientSocket.close();
    }

    public void send(int port, String data) {
        try {
            sendUnsafe(port, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
