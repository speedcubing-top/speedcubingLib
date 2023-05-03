package top.speedcubing.lib.utils.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
    private final String host;
    private final int timeout;

    public TCPClient(String host, int timeout) {
        this.host = host;
        this.timeout = timeout;
    }

    public void send(int port, byte[] data) {
        try {
            sendUnsafe(port, data);
        } catch (IOException e) {
        }
    }

    public void sendUnsafe(int port, byte[] data) throws IOException {
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port), timeout);
        clientSocket.getOutputStream().write(data);
        clientSocket.close();
    }
}
