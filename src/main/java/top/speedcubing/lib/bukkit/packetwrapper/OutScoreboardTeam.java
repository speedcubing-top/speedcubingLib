package top.speedcubing.lib.bukkit.packetwrapper;

import java.util.Collection;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import top.speedcubing.lib.utils.ReflectionUtils;

public class OutScoreboardTeam {
    public final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

    public OutScoreboardTeam() {
    }

    public OutScoreboardTeam a(String name) {
        ReflectionUtils.setField(packet, "a", name);
        return this;
    }

    public OutScoreboardTeam b(String displayName) {
        ReflectionUtils.setField(packet, "b", displayName);
        return this;
    }

    public OutScoreboardTeam c(String prefix) {
        ReflectionUtils.setField(packet, "c", prefix);
        return this;
    }

    public OutScoreboardTeam d(String suffix) {
        ReflectionUtils.setField(packet, "d", suffix);
        return this;
    }

    public OutScoreboardTeam e(String nametag) {
        ReflectionUtils.setField(packet, "e", nametag);
        return this;
    }

    public OutScoreboardTeam f(int f) {
        ReflectionUtils.setField(packet, "f", f);
        return this;
    }

    public OutScoreboardTeam g(Collection<String> players) {
        ReflectionUtils.setField(packet, "g", players);
        return this;
    }

    public OutScoreboardTeam h(int h) {
        ReflectionUtils.setField(packet, "h", h);
        return this;
    }

    public OutScoreboardTeam i(int i) {
        ReflectionUtils.setField(packet, "i", i);
        return this;
    }
}
