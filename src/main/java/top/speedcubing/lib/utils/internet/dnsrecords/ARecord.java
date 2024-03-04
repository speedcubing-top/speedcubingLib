package top.speedcubing.lib.utils.internet.dnsrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

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

    public static List<ARecord> lookup(String name) throws Exception {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            InitialDirContext idc = new InitialDirContext(env);
            Attributes attrs = idc.getAttributes(name, new String[]{"A"});
            Attribute attr = attrs.get("A");
            List<ARecord> records = new ArrayList<>();
            if (attr != null)
                for (String s : attr.toString().substring(3).split(", "))
                    records.add(new ARecord(name, s));
            return records;
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}