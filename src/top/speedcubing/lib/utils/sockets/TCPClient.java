package top.speedcubing.lib.utils.sockets;

import java.io.*;
import java.net.*;

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

    public byte[] sendAndRead(int port, byte[] data, int buffer) throws IOException {
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(host, port), timeout);
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.write(data);
        out.flush();
        byte[] b = new byte[buffer];
        in.read(b);
        out.close();
        in.close();
        return b;
    }
}
