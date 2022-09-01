package top.speedcubing.lib.api;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerPing {
    public static String pingServer(String host, int port, int timeout) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), timeout);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(handshake_bytes);
        handshake.writeByte(0x00);
        writeVarInt(handshake, 4);
        writeVarInt(handshake, host.length());
        handshake.writeBytes(host);
        handshake.writeShort(port);
        writeVarInt(handshake, 1);
        writeVarInt(out, handshake_bytes.size());
        out.write(handshake_bytes.toByteArray());
        out.writeByte(0x01);
        out.writeByte(0x00);
        readVarInt(in);
        int id = readVarInt(in);
        if (id == -1) throw new IOException("Server prematurely ended stream.");
        if (id != 0x00) throw new IOException("Server returned invalid packet.");
        int length = readVarInt(in);
        if (length == -1) throw new IOException("Server prematurely ended stream.");
        if (length == 0) throw new IOException("Server returned unexpected value.");
        byte[] data = new byte[length];
        in.readFully(data);
        String json = new String(data, StandardCharsets.UTF_8);
        out.writeByte(0x09);
        out.writeByte(0x01);
        out.writeLong(System.currentTimeMillis());
        readVarInt(in);
        id = readVarInt(in);
        if (id == -1) throw new IOException("Server prematurely ended stream.");
        if (id != 0x01) throw new IOException("Server returned invalid packet.");
        handshake.close();
        handshake_bytes.close();
        out.close();
        in.close();
        socket.close();
        return json;
    }

    private static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5)
                throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128)
                break;
        }
        return i;
    }

    private static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
