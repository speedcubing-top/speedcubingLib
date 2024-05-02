package top.speedcubing.lib.api.mcserverping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import top.speedcubing.lib.minecraft.packet.HandshakePacket;
import top.speedcubing.lib.minecraft.packet.MinecraftPacket;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;
import top.speedcubing.lib.utils.IOUtils;
import top.speedcubing.lib.utils.internet.dnsrecords.ARecord;
import top.speedcubing.lib.utils.internet.dnsrecords.CNAMERecord;
import top.speedcubing.lib.utils.internet.dnsrecords.DNSRecord;
import top.speedcubing.lib.utils.internet.dnsrecords.SRVRecord;

public class ServerPingRequest {

    private final HandshakePacket handshake = new HandshakePacket(0, null, 25565, 1);
    private boolean dnsLookup = true;
    private boolean srvLookup = true;
    private int timeout = 1000;

    public ServerPingRequest serverAddress(String hostname) {
        handshake.setServerAddress(hostname);
        return this;
    }

    public ServerPingRequest serverPort(int port) {
        handshake.setServerPort(port);
        return this;
    }

    public ServerPingRequest protocolVersion(int protocol) {
        handshake.setProtocolVersion(protocol);
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
        String srvHostname = handshake.getServerAddress();
        int srvPort = handshake.getServerPort();
        boolean srv = false;
        List<DNSRecord> records = new ArrayList<>();
        if (srvLookup) {
            try {
                SRVRecord srvRecord = SRVRecord.lookup("_minecraft._tcp." + srvHostname);
                if (srvRecord != null) {
                    srvHostname = srvRecord.getTarget();
                    srvPort = srvRecord.getPort();
                    if (dnsLookup) {
                        records.add(srvRecord);
                        srv = true;
                        List<DNSRecord> cname = CNAMERecord.lookupAll(srvHostname);
                        records.addAll(cname);
                        srvHostname = cname.isEmpty() ? srvHostname : cname.get(cname.size() - 1).toCNAME().getTarget();
                    }
                }
                List<ARecord> aRecord = ARecord.lookup(srvHostname);
                if (!aRecord.isEmpty()) {
                    records.addAll(aRecord);
                    srvHostname = aRecord.get(0).getIPv4();
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

        DataInputStream sokcetIn = new DataInputStream(socket.getInputStream());
        DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());

        MinecraftPacket packet = new MinecraftPacket(0, handshake.toByteArray());

        ByteArrayDataBuilder buffer = new ByteArrayDataBuilder()
                .write(packet.toByteArray())
                .writeVarInt(1)
                .writeVarInt(0);

        socketOut.write(buffer.toByteArray());

        IOUtils.readVarInt(sokcetIn);

        int id = IOUtils.readVarInt(sokcetIn);
        io(id == -1, "Server prematurely ended stream.");
        io(id != 0, "Server returned invalid packet.");

        int length = IOUtils.readVarInt(sokcetIn);
        io(length == -1, "Server prematurely ended stream.");
        io(length == 0, "Server returned unexpected value.");

        byte[] dt = new byte[length];
        sokcetIn.readFully(dt);
        socketOut.writeByte(0x09);
        socketOut.writeByte(0x01);
        socketOut.writeLong(System.currentTimeMillis());

        IOUtils.readVarInt(sokcetIn);
        id = IOUtils.readVarInt(sokcetIn);
        io(id == -1, "Server prematurely ended stream.");
        io(id != 0x01, "Server returned invalid packet.");
        return new ServerPingResponse(this.handshake.getServerAddress(), this.handshake.getServerPort(), srvHostname, srvPort, ping, srv, new ServerPingInfo(new String(dt)), records);
    }

    private static void io(boolean f, String s) throws Exception {
        if (f)
            throw new Exception(s);
    }
}
