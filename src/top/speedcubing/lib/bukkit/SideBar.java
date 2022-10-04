package top.speedcubing.lib.bukkit;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SideBar implements Listener {
    public static final Map<Player, SideBar> perPlayerSidebar = new HashMap<>();

    public static SideBar getSidebar(Player player) {
        return perPlayerSidebar.get(player);
    }

    public static Collection<SideBar> getSidebars() {
        return perPlayerSidebar.values();
    }

    public final Scoreboard scoreboard;
    public final Objective objective;
    public final Player player;
    int changer = -1;
    int line = 1;
    Map<Integer, String> lines = new HashMap<>();
    Map<Integer, Set<String>> scores = new HashMap<>();

    public SideBar(Player player, String displayName) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayName);
        this.player = player;
        perPlayerSidebar.put(player, this);
    }

    public void create() {
        player.setScoreboard(scoreboard);
    }

    SideBar setPrefixSuffix(String str, String team) {
        Team t = scoreboard.getTeam(team);
        String[] result = split(str);
        if (!result[0].equals(t.getPrefix()))
            t.setPrefix(result[0]);
        if (!result[1].equals(t.getSuffix()))
            t.setSuffix(result[1]);
        return this;
    }

    public SideBar staticLine(String str, int score) {
        if (str.matches("^\\w{1,16}$"))
            throw new IllegalArgumentException();
        return add(str, score, true);
    }

    SideBar add(String str, int score, boolean isStaticLine) {
        objective.getScore(str).setScore(score);
        if (!isStaticLine) {
            lines.put(line, str);
            Set<String> s = scores.get(score);
            if (s != null)
                s.add(str);
            else scores.put(score, Sets.newHashSet(str));
        }
        line += 1;
        return this;
    }

    public SideBar changeableLine(String prefix, String suffix, int score) {
        changer += 1;
        String str = "§" + "!0123456789abcdef".charAt(changer) + "§f";
        Team x = scoreboard.registerNewTeam(str);
        x.addEntry(str);
        x.setPrefix(prefix);
        x.setSuffix(suffix);
        return add(str, score, false);
    }

    public SideBar changeableLine(String str, int score) {
        String[] result = split(str);
        return changeableLine(result[0], result[1], score);
    }

    public SideBar setLine(String str, int line) {
        return setPrefixSuffix(str, lines.get(line));
    }

    public SideBar setLineByScore(String str, int score) {
        for (String s : scores.get(score)) {
            setPrefixSuffix(str, s);
        }
        return this;
    }

    private static String[] split(String str) {
        String prefix;
        String suffix = "";
        if (str.length() <= 16)
            prefix = str;
        else {
            boolean splitted = false;
            if (str.charAt(15) == '§') {
                prefix = str.substring(0, 15);
                if ((int) str.charAt(16) < 107)
                    suffix = str.substring(15);
            } else {
                splitted = true;
                prefix = str.substring(0, 16);
            }
            for (int j = splitted ? 14 : 13; j >= 0; j--) {
                if (str.charAt(j) == '§' && (int) str.charAt(j + 1) < 107) {
                    suffix = "§" + str.charAt(j + 1) + str.substring(splitted ? 16 : 15);
                    break;
                }
            }
            suffix = suffix.equals("") ? str.substring(splitted ? 16 : 15) : suffix;
        }
        return new String[]{prefix, suffix};
    }
}
