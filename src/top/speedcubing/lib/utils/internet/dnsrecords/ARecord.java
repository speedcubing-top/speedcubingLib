package top.speedcubing.lib.utils.internet.dnsrecords;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.*;

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
                    records.add(new ARecord(name,s));
            return records;
        } catch (
                NameNotFoundException e) {
            return null;
        }
    }

    public static void main(String[] s) throws Exception {
        System.out.println(lookup("mt.mc.production.hypixel.io"));
    }
}