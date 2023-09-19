package top.speedcubing.lib.utils.internet.dnsrecords;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.Properties;

public class ARecord extends DNSRecord {
    private final String name;
    private final String IPv4;

    public ARecord(String name, String IPv4) {
        this.name = name;
        this.IPv4 = IPv4;
    }

    public String getName() {
        return name;
    }

    public String getIPv4() {
        return IPv4;
    }

    @Override
    public String toString() {
        return "A{name:\"" + name + "\",IPv4:\"" + IPv4 + "\"}";
    }

    public static ARecord lookup(String name) throws Exception {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            InitialDirContext idc = new InitialDirContext(env);
            Attributes attrs = idc.getAttributes(name, new String[]{"A"});
            Attribute attr = attrs.get("A");
            if (attr == null)
                return null;
            return new ARecord(name, ((String) attr.get()).replaceFirst("\\.$", ""));
        } catch (
                NameNotFoundException e) {
            return null;
        }
    }
}