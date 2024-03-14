package top.speedcubing.lib.utils.internet;

public class HostAndPort {
    private final String host;
    private final int port;

    public HostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public HostAndPort(String hostAndPort) {
        String[] spl = hostAndPort.split(":");
        this.host = spl[0];
        this.port = Integer.parseInt(spl[1]);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object addr2) {
        if (!(addr2 instanceof HostAndPort))
            return false;
        HostAndPort addr = (HostAndPort) addr2;
        return addr.getHost().equals(getHost()) && addr.getPort() == getPort();
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
