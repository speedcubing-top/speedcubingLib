package speedcubing.lib.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class SideBar {
    public static Map<Player, SideBar> perPlayerSidebar = new HashMap<>();

    public static SideBar getSidebar(Player player) {
        return perPlayerSidebar.get(player);
    }

    public final Scoreboard scoreboard;
    public final Objective objective;
    private int changer = -1;
    private int line = 1;
    private final Player player;
    public Map<Integer, String> lines = new HashMap<>();

    public SideBar(Player player, String displayName) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayName);
        this.player = player;
        perPlayerSidebar.put(player, this);
    }

    public void create() {
        this.player.setScoreboard(this.scoreboard);
    }

    public SideBar staticLine(String str, int score) {
        if (str.matches("^\\w{1,16}$"))
            throw new IllegalArgumentException();
        objective.getScore(str).setScore(score);
        lines.put(line, str);
        line += 1;
        return this;
    }

    public SideBar changeableLine(String prefix, String suffix, int score) {
        changer += 1;
        Team x = scoreboard.registerNewTeam("§" + changer + "§f");
        x.addEntry("§" + changer + "§f");
        x.setPrefix(prefix);
        x.setSuffix(suffix);
        return staticLine("§" + changer + "§f", score);
    }

    public SideBar changeableLine(String str, int score) {
        String[] result = split(str);
        return changeableLine(result[0], result[1], score);
    }

    public SideBar setLine(Player player, String str, int line) {
        Team t = player.getScoreboard().getTeam(lines.get(line));
        String[] result = split(str);
        t.setPrefix(result[0]);
        t.setSuffix(result[1]);
        return this;
    }

    private static String[] split(String str) {
        String prefix;
        String suffix = "";
        if (str.length() <= 16)
            prefix = str;
        else {
            if (str.charAt(15) == '§') {
                if ((int) str.charAt(16) < 107) {
                    prefix = str.substring(0, 15);
                    suffix = str.substring(15);
                } else {
                    prefix = str.substring(0, 15);
                    boolean b = true;
                    for (int j = 13; j >= 0; j--) {
                        if (str.charAt(j) == '§' && (int) str.charAt(j + 1) < 107) {
                            b = false;
                            suffix = "§" + str.charAt(j + 1) + str.substring(15);
                        }
                    }
                    if (b) suffix = str.substring(15);
                }
            } else {
                prefix = str.substring(0, 16);
                boolean b = true;
                for (int j = 14; j >= 0; j--) {
                    if (str.charAt(j) == '§' && (int) str.charAt(j + 1) < 107) {
                        suffix = "§" + str.charAt(j + 1) + str.substring(16);
                        b = false;
                        break;
                    }
                }
                if (b) suffix = str.substring(16);
            }
        }
        return new String[]{prefix, suffix};
    }
}
