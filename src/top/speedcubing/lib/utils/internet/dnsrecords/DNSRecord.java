package top.speedcubing.lib.utils.internet.dnsrecords;

public class DNSRecord {
    public ARecord toA() {
        return (ARecord) this;
    }
    public CNAMERecord toCNAME() {
        return (CNAMERecord) this;
    }
    public SRVRecord toSRV() {
        return (SRVRecord) this;
    }
}
