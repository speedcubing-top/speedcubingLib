package speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import speedcubing.lib.utils.Reflections;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.util.Collection;

public class OutScoreboardTeam {
    public final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

    public OutScoreboardTeam() {
    }

    public OutScoreboardTeam a(String name) {
        Reflections.setField(packet, "a", name);
        return this;
    }

    public OutScoreboardTeam b(String displayName) {
        Reflections.setField(packet, "b", displayName);
        return this;
    }

    public OutScoreboardTeam c(String prefix) {
        Reflections.setField(packet, "c", prefix);
        return this;
    }

    public OutScoreboardTeam d(String suffix) {
        Reflections.setField(packet, "d", suffix);
        return this;
    }

    public OutScoreboardTeam e(String e) {
        Reflections.setField(packet, "e", e);
        return this;
    }

    public OutScoreboardTeam f(int f) {
        Reflections.setField(packet, "f", f);
        return this;
    }

    public OutScoreboardTeam g(Collection<String> players) {
        Reflections.setField(packet, "g", players);
        return this;
    }

    public OutScoreboardTeam h(int h) {
        Reflections.setField(packet, "h", h);
        return this;
    }

    public OutScoreboardTeam i(int i) {
        Reflections.setField(packet, "i", i);
        return this;
    }
}
