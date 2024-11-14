package top.speedcubing.lib.utils.sockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import top.speedcubing.lib.utils.bytes.IOUtils;
import top.speedcubing.lib.utils.internet.HostAndPort;

public class TCPClient {

    public static void write(HostAndPort hostAndPort, byte[] data) {
        write(hostAndPort, data, 1000);
    }

    public static void write(HostAndPort hostAndPort, byte[] data, int timeout) {
        try {
            Socket client = new Socket();
            client.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), timeout);
            client.getOutputStream().write(data);
            IOUtils.closeQuietly(client);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] writeAndReadAll(HostAndPort hostAndPort, byte[] data) {
        return writeAndReadAll(hostAndPort, data, 1000);
    }

    public static byte[] writeAndReadAll(HostAndPort hostAndPort, byte[] data, int timeout) {
        try {
            Socket client = new Socket();
            client.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), timeout);
            client.getOutputStream().write(data);
            byte[] b = client.getInputStream().readAllBytes();
            IOUtils.closeQuietly(client);
            return b;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void tryWrite(HostAndPort hostAndPort, byte[] data) throws IOException {
        tryWrite(hostAndPort, data, 1000);
    }

    public static void tryWrite(HostAndPort hostAndPort, byte[] data, int timeout) throws IOException {
        Socket client = new Socket();
        client.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), timeout);
        client.getOutputStream().write(data);
        IOUtils.closeQuietly(client);
    }

    public static byte[] tryWriteAndReadAll(HostAndPort hostAndPort, byte[] data) throws IOException {
        return tryWriteAndReadAll(hostAndPort, data, 1000);
    }

    public static byte[] tryWriteAndReadAll(HostAndPort hostAndPort, byte[] data, int timeout) throws IOException {
        Socket client = new Socket();
        client.connect(new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort()), timeout);
        client.getOutputStream().write(data);
        byte[] b = client.getInputStream().readAllBytes();
        IOUtils.closeQuietly(client);
        return b;
    }
}
