package cubing.bukkit;

import cubing.utils.Reflections;
import net.minecraft.server.v1_8_R3.*;

import java.util.Collection;

public class PacketWrapper {

    public static PacketPlayOutScoreboardTeam packetPlayOutScreboardTeam(String team, String prefix, String suffix, String Enum, Collection<String> entries, int JoinorLeave) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        if (team != null)
            Reflections.setField(packet, "a", team);
        if (prefix != null)
            Reflections.setField(packet, "c", prefix);
        if (suffix != null)
            Reflections.setField(packet, "d", suffix);
        if (Enum != null)
            Reflections.setField(packet, "e", Enum);
        if (entries != null)
            Reflections.setField(packet, "g", entries);
        if (JoinorLeave != -1)
            Reflections.setField(packet, "h", JoinorLeave);
        return packet;
    }

    public static PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation(int id, byte Byte) {
        PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation();
        Reflections.setField(packet, "a", id);
        Reflections.setField(packet, "b", Byte);
        return packet;
    }

    /*
    1 : damage color
    0 : swing arms
    */
    public static net.minecraft.server.v1_8_R3.PacketPlayOutAnimation packetPlayOutAnimation(int id, byte animation) {
        net.minecraft.server.v1_8_R3.PacketPlayOutAnimation packet = new net.minecraft.server.v1_8_R3.PacketPlayOutAnimation();
        Reflections.setField(packet, "a", id);
        Reflections.setField(packet, "b", animation);
        return packet;
    }

    public static PacketPlayOutPlayerListHeaderFooter PacketPlayOutPlayerListHeaderFooter(String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Reflections.setField(packet, "a", new ChatComponentText(header));
        Reflections.setField(packet, "b", new ChatComponentText(footer));
        return packet;
    }
}
