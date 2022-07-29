package speedcubing.lib.bukkit.packetwrapper;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import speedcubing.lib.utils.Reflections;

public class OutScoreboardScore {
    public final PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();

    public OutScoreboardScore() {
    }

    public OutScoreboardScore a(String playerName) {
        Reflections.setField(packet, "a", playerName);
        return this;
    }

    public OutScoreboardScore b(String objectiveName) {
        Reflections.setField(packet, "b", objectiveName);
        return this;
    }

    public OutScoreboardScore c(int score) {
        Reflections.setField(packet, "c", score);
        return this;
    }

    public OutScoreboardScore d(PacketPlayOutScoreboardScore.EnumScoreboardAction action) {
        Reflections.setField(packet, "d", action);
        return this;
    }
}
