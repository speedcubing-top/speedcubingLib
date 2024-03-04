package top.speedcubing.lib.utils.internet.protocol;

import java.net.StandardProtocolFamily;
import top.speedcubing.lib.utils.ByteArrayDataBuilder;

public class HAProxyProtocol {

    public static byte[] v1(StandardProtocolFamily protocolFamily, String sourceAddr, String destAddr, int sourcePort, int destPort) {
        ByteArrayDataBuilder builder = new ByteArrayDataBuilder();
        builder.write(new byte[]{80, 82, 79, 88, 89, 32, 84, 67, 80});
        if (protocolFamily == StandardProtocolFamily.INET)
            builder.write(52);
        else if (protocolFamily == StandardProtocolFamily.INET6)
            builder.write(54);
        builder.write(32);
        builder.write(sourceAddr.getBytes());
        builder.write(32);
        builder.write(destAddr.getBytes());
        builder.write(32);
        builder.write(String.valueOf(sourcePort).getBytes());
        builder.write(32);
        builder.write(String.valueOf(destPort).getBytes());
        builder.write(13);
        builder.write(10);
        return builder.toByteArray();
    }

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
