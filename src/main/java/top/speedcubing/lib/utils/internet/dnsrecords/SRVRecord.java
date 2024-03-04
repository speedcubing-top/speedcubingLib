package top.speedcubing.lib.utils.internet.dnsrecords;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Properties;

public class SRVRecord extends DNSRecord {
    private final String host;
    private final int priority;
    private final int weight;
    private final int port;
    private final String target;

    public SRVRecord(String host, int priority, int weight, int port, String target) {
        this.host = host;
        this.priority = priority;
        this.weight = weight;
        this.port = port;
        this.target = target;
    }

    public String getHost() {
        return host;
    }

    public int getPriority() {
        return priority;
    }

    public int getWeight() {
        return weight;
    }

    public int getPort() {
        return port;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "SRV{host:\"" + host + "\",priority:" + priority + ",weight:" + weight + ",port:" + port + ",target:\"" + target + "\"}";
    }

    public static SRVRecord lookup(String address) throws Exception {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            Attributes attrs = new InitialDirContext(env).getAttributes(address, new String[]{"SRV"});
            Attribute attr = attrs.get("srv");
            if (attr == null)
                return null;
            else {
                String[] srvRecord = ((String) attr.get(0)).split(" ");
                return new SRVRecord(address, Integer.parseInt(srvRecord[0]), Integer.parseInt(srvRecord[1]), Integer.parseInt(srvRecord[2]), srvRecord[3].replaceFirst("\\.$", ""));
            }
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}
