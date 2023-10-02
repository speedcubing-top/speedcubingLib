package top.speedcubing.lib.utils.internet.protocol;

import top.speedcubing.lib.utils.ByteArrayDataBuilder;

public class HAProxyProtcol {
    public static byte[] v2(String sourceIPV4, String destIPV4, int sourcePort, int destPort) {
        ByteArrayDataBuilder builder = new ByteArrayDataBuilder();
        builder.write(new byte[]{13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10, 33, 17, 0, 12});
        String[] s = sourceIPV4.split("\\.");
        builder.writeByte(Integer.parseInt(s[0]));
        builder.writeByte(Integer.parseInt(s[1]));
        builder.writeByte(Integer.parseInt(s[2]));
        builder.writeByte(Integer.parseInt(s[3]));
        s = destIPV4.split("\\.");
        builder.writeByte(Integer.parseInt(s[0]));
        builder.writeByte(Integer.parseInt(s[1]));
        builder.writeByte(Integer.parseInt(s[2]));
        builder.writeByte(Integer.parseInt(s[3]));
        builder.writeShort((short) sourcePort);
        builder.writeShort((short) destPort);
        return builder.toByteArray();
    }
}
