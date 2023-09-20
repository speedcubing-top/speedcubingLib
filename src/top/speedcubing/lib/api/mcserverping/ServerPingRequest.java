package top.speedcubing.lib.api.mcserverping;

import top.speedcubing.lib.utils.IOUtils;
import top.speedcubing.lib.utils.internet.dnsrecords.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerPingRequest {

    private String hostname;
    private int port = 25565;
    private int protocol = 47;
    private boolean dnsLookup = true;
    private int timeout = 1000;

    public ServerPingRequest() {
    }

    public ServerPingRequest hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ServerPingRequest port(int port) {
        this.port = port;
        return this;
    }

    public ServerPingRequest protocol(int protocol) {
        this.protocol = protocol;
        return this;
    }

    public ServerPingRequest dnsLookup(boolean dnsLookup) {
        this.dnsLookup = dnsLookup;
        return this;
    }

    public ServerPingRequest timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public ServerPingResponse ping() throws Exception {
        String srvHostname = hostname;
        int srvPort = port;
        boolean srv = false;
        List<DNSRecord> records = new ArrayList<>();
        try {
            SRVRecord srvRecord = SRVRecord.lookup("_minecraft._tcp." + hostname);
            srvHostname = srvRecord.getTarget();
            srvPort = srvRecord.getPort();
            if (dnsLookup) {
                records.add(srvRecord);
                srv = true;
                List<DNSRecord> cname = CNAMERecord.lookupAll(hostname);
                records.addAll(cname);
                records.add(ARecord.lookup(cname.isEmpty() ? hostname : cname.get(cname.size() - 1).toCNAME().getTarget()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long ping;
        Socket socket = new Socket();
        long start = System.currentTimeMillis();
        socket.connect(new InetSocketAddress(hostname, port), timeout);
        ping = System.currentTimeMillis() - start;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(handshake_bytes);

        handshake.writeByte(0x00);
        IOUtils.writeVarInt(handshake, protocol);
        IOUtils.writeVarInt(handshake, hostname.length());
        handshake.writeBytes(hostname);
        handshake.writeShort(port);
        IOUtils.writeVarInt(handshake, 1);
        IOUtils.writeVarInt(out, handshake_bytes.size());
        out.write(handshake_bytes.toByteArray());
        out.writeByte(0x01);
        out.writeByte(0x00);

        IOUtils.readVarInt(in);
        int id = IOUtils.readVarInt(in);

        io(id == -1, "Server prematurely ended stream.");
        io(id != 0x00, "Server returned invalid packet.");

        int length = IOUtils.readVarInt(in);
        io(length == -1, "Server prematurely ended stream.");
        io(length == 0, "Server returned unexpected value.");

        byte[] data = new byte[length];
        in.readFully(data);
        out.writeByte(0x09);
        out.writeByte(0x01);
        out.writeLong(System.currentTimeMillis());

        IOUtils.readVarInt(in);
        id = IOUtils.readVarInt(in);
        io(id == -1, "Server prematurely ended stream.");
        io(id != 0x01, "Server returned invalid packet.");
        return new ServerPingResponse(hostname, port, srvHostname, srvPort, ping, srv, new ServerPingInfo(new String(data)), records);
    }

    private static void io(boolean f, String s) throws Exception {
        if (f)
            throw new Exception(s);
    }
}