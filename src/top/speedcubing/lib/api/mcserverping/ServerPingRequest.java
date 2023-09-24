package top.speedcubing.lib.api.mcserverping;

import top.speedcubing.lib.utils.*;
import top.speedcubing.lib.utils.internet.dnsrecords.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerPingRequest {
    public static void main(String[] args) {

        try {
            ServerPingResponse s = new ServerPingRequest().hostname("speedcubing.top").ping();
            System.out.println(s.getServerInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hostname;
    private int port = 25565;
    private int protocol = 47;
    private boolean dnsLookup = true;
    private boolean srvLookup = true;
    private int timeout = 1000;

    public ServerPingRequest() {
    }

    public ServerPingRequest hostname(String hostname) {
        String[] s = hostname.split(":");
        this.hostname = s[0];
        this.port = s.length == 2 ? Integer.parseInt(s[1]) : 25565;
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

    public ServerPingRequest srvLookup(boolean srvLookup) {
        this.srvLookup = srvLookup;
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
        if (srvLookup) {
            try {
                SRVRecord srvRecord = SRVRecord.lookup("_minecraft._tcp." + hostname);
                if (srvRecord != null) {
                    srvHostname = srvRecord.getTarget();
                    srvPort = srvRecord.getPort();
                    if (dnsLookup) {
                        records.add(srvRecord);
                        srv = true;
                        List<DNSRecord> cname = CNAMERecord.lookupAll(srvHostname);
                        records.addAll(cname);
                        ARecord aRecord = ARecord.lookup(cname.isEmpty() ? srvHostname : cname.get(cname.size() - 1).toCNAME().getTarget());
                        if (aRecord != null) {
                            records.add(aRecord);
                            srvHostname = aRecord.getIPv4();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long ping;
        Socket socket = new Socket();
        long start = System.currentTimeMillis();
        socket.connect(new InetSocketAddress(srvHostname, srvPort), timeout);
        ping = System.currentTimeMillis() - start;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        ByteArrayDataBuilder data = new ByteArrayDataBuilder()
                .writeVarInt(0)
                .writeVarInt(protocol)
                .writeVarInt(hostname.length())
                .write(hostname.getBytes())
                .writeShort((short) 25565)
                .writeVarInt(1);
        ByteArrayDataBuilder handshake = new ByteArrayDataBuilder()
                .writeVarInt(data.toByteArray().length)
                .write(data.toByteArray())
                .writeVarInt(1)
                .writeVarInt(0);
        out.write(handshake.toByteArray());

        IOUtils.readVarInt(in);
        int id = IOUtils.readVarInt(in);

        io(id == -1, "Server prematurely ended stream.");
        io(id != 0x00, "Server returned invalid packet.");

        int length = IOUtils.readVarInt(in);
        io(length == -1, "Server prematurely ended stream.");
        io(length == 0, "Server returned unexpected value.");

        byte[] dt = new byte[length];
        in.readFully(dt);
        out.writeByte(0x09);
        out.writeByte(0x01);
        out.writeLong(System.currentTimeMillis());

        IOUtils.readVarInt(in);
        id = IOUtils.readVarInt(in);
        io(id == -1, "Server prematurely ended stream.");
        io(id != 0x01, "Server returned invalid packet.");
        return new ServerPingResponse(hostname, port, srvHostname, srvPort, ping, srv, new ServerPingInfo(new String(dt)), records);
    }

    private static void io(boolean f, String s) throws Exception {
        if (f)
            throw new Exception(s);
    }
}
