package speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import speedcubing.lib.utils.Reflections;

public class OutScoreboardScore {
    public final PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();

    public OutScoreboardScore() {
    }

    public OutScoreboardScore a(String string) {
        Reflections.setField(packet, "a", string);
        return this;
    }

    public OutScoreboardScore b(String name) {
        Reflections.setField(packet, "b", name);
        return this;
    }

    public OutScoreboardScore c(String c) {
        Reflections.setField(packet, "c", c);
        return this;
    }

    public OutScoreboardScore d(PacketPlayOutScoreboardScore.EnumScoreboardAction action) {
        Reflections.setField(packet, "d", action);
        return this;
    }
}
