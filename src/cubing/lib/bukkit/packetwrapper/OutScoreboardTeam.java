package cubing.lib.bukkit.packetwrapper;

import cubing.lib.utils.Reflections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.util.Collection;

public class OutScoreboardTeam {
    public static PacketPlayOutScoreboardTeam a(String name, int h) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        Reflections.setField(packet, "a", name);
        Reflections.setField(packet, "h", h);
        return packet;
    }

    public static PacketPlayOutScoreboardTeam a(String name, String prefix, Collection<String> players, int join) {
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        Reflections.setField(packet, "a", name);
        Reflections.setField(packet, "c", prefix);
        Reflections.setField(packet, "g", players);
        Reflections.setField(packet, "h", join);
        return packet;
    }
}
