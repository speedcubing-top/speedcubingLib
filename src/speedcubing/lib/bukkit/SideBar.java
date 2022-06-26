package speedcubing.lib.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SideBar {
    public Scoreboard scoreboard;
    public Objective objective;
    private int changer = -1;

    public SideBar(String displayName) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(displayName);
    }

    public SideBar staticLine(String str, int score) {
        if (str.matches("^\\w{1,16}$"))
            throw new IllegalArgumentException();
        objective.getScore(str).setScore(score);
        return this;
    }

    public SideBar changeableLine(String prefix, String team, String suffix, int score) {
        Team x = scoreboard.registerNewTeam(team);
        x.addEntry(team);
        x.setPrefix(prefix);
        x.setSuffix(suffix);
        return staticLine(team, score);
    }

    public SideBar changeableLine(String prefix, String suffix, int score) {
        changer += 1;
        return changeableLine(prefix, "§" + changer + "§f", suffix, score);
    }

    public SideBar changeableLine(String str, int score) {
        String[] result = split(str);
        return changeableLine(result[0], result[1], score);
    }

    public static Scoreboard setPrefix(Player player, String prefix, int index) {
        return setPrefix(player, prefix, "§" + index + "§f");
    }

    public static Scoreboard setSuffix(Player player, String suffix, int index) {
        return setSuffix(player, suffix, "§" + index + "§f");
    }

    public static Scoreboard setLine(Player player, String str, int index) {
        return setLine(player, str, "§" + index + "§f");
    }

    public static Scoreboard setPrefix(Player player, String prefix, String team) {
        Scoreboard board = player.getScoreboard();
        Team t = board.getTeam(team);
        if (t != null)
            t.setPrefix(prefix);
        return board;
    }

    public static Scoreboard setSuffix(Player player, String suffix, String team) {
        Scoreboard board = player.getScoreboard();
        Team t = board.getTeam(team);
        if (t != null)
            t.setSuffix(suffix);
        return board;

    }

    public static Scoreboard setLine(Player player, String str, String team) {
        Scoreboard board = player.getScoreboard();
        Team t = board.getTeam(team);
        if (t != null) {
            String[] result = split(str);
            t.setPrefix(result[0]);
            t.setSuffix(result[1]);
        }
        return board;
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
