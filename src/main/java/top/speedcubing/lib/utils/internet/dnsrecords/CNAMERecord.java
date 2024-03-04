package top.speedcubing.lib.utils.internet.dnsrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class CNAMERecord extends DNSRecord {
    private final String name;
    private final String target;

    public CNAMERecord(String name, String target) {
        this.name = name;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "CNAME{name:\"" + name + "\",target:\"" + target + "\"}";
    }

    public static CNAMERecord lookup(String name) throws Exception {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
            InitialDirContext idc = new InitialDirContext(env);
            Attributes attrs = idc.getAttributes(name, new String[]{"CNAME"});
            Attribute attr = attrs.get("CNAME");
            if (attr == null)
                return null;
            return new CNAMERecord(name, ((String) attr.get()).replaceFirst("\\.$", ""));
        } catch (
                NameNotFoundException e) {
            return null;
        }
    }

    public static List<DNSRecord> lookupAll(String name) throws Exception {
        return lookupAll(name, new ArrayList<>());
    }

    private static List<DNSRecord> lookupAll(String name, List<DNSRecord> cnameRecords) throws Exception {
        CNAMERecord current = CNAMERecord.lookup(name);
        if (current == null)
            return cnameRecords;
        else {
            cnameRecords.add(current);
            return lookupAll(current.getTarget(), cnameRecords);
        }
    }
}