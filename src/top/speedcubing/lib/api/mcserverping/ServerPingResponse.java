package top.speedcubing.lib.api.mcserverping;

import top.speedcubing.lib.utils.internet.dnsrecords.DNSRecord;

import java.util.List;

public class ServerPingResponse {
    private final String SRVHostname;
    private final int SRVPort;
    private final long ping;
    private final String hostname;
    private final int port;
    private final boolean srv;
    private final ServerPingInfo response;
    private final List<DNSRecord> records;

    ServerPingResponse(String hostname, int port, String SRVHostname, int SRVPort, long ping, boolean srv, ServerPingInfo response, List<DNSRecord> records) {
        this.SRVHostname = SRVHostname;
        this.SRVPort = SRVPort;
        this.hostname = hostname;
        this.port = port;
        this.ping = ping;
        this.srv = srv;
        this.response = response;
        this.records = records;
    }

    public String getSrvHostname() {
        return SRVHostname;
    }

    public int getSRVPort() {
        return SRVPort;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public long getPing() {
        return ping;
    }

    public boolean isSrv() {
        return srv;
    }

    public ServerPingInfo getResponse() {
        return response;
    }

    public List<DNSRecord> getRecords() {
        return records;
    }
}
