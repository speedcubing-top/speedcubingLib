package top.speedcubing.lib.utils.internet.dnsrecords;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;

public abstract class DNSRecord {
    private static InitialDirContext context;

    static {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        try {
            context = new InitialDirContext(env);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public ARecord toA() {
        return (ARecord) this;
    }

    public CNAMERecord toCNAME() {
        return (CNAMERecord) this;
    }

    public SRVRecord toSRV() {
        return (SRVRecord) this;
    }

    static Attribute lookup(String address, String type) throws NamingException {
        return context.getAttributes(address, new String[]{type}).get(type);
    }
}
