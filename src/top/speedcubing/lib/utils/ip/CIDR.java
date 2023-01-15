package top.speedcubing.lib.utils.ip;


/**
 CIDR c = new CIDR("10.0.0.0/24");
 c.to // [10,0,0,255]
 c.from // [10,0,0,0]
 c.contains("10.0.0.1") // true
 c.contains("0.0.0.0") // false
 **/
public class CIDR {
    int[] from = new int[4];
    int[] to = new int[4];

    public CIDR(String cidr) {
        String[] ip = cidr.split("/");
        String[] buf = ip[0].split("\\.");
        int value = 0xffffffff << (32 - Integer.parseInt(ip[1]));
        byte[] subnet = {(byte) (value >>> 24), (byte) (value >> 16 & 0xff), (byte) (value >> 8 & 0xff), (byte) (value & 0xff)};
        for (int i = 0; i < 4; i++) {
            from[i] = Byte.toUnsignedInt((byte) (Integer.parseInt(buf[i]) & subnet[i]));
            to[i] = Byte.toUnsignedInt((byte) (Integer.parseInt(buf[i]) | ~subnet[i]));
        }
    }

    public String[] getRange() {
        return new String[]{from[0] + "." + from[1] + "." + from[2] + "." + from[3], to[0] + "." + to[1] + "." + to[2] + "." + to[3]};
    }

    public boolean contains(String ip) {
        String[] tg = ip.split("\\.");
        int v;
        for (int i = 0; i < 4; i++) {
            v = Integer.parseInt(tg[i]);
            if (from[i] > v || v > to[i])
                return false;
        }
        return true;
    }
}
