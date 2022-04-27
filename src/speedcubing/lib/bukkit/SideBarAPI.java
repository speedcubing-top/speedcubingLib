package speedcubing.lib.bukkit;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SideBarAPI {
    public static Scoreboard sidebarTeams(Scoreboard scoreboard, String[][] strings, int[] scores) {
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        for (int i = 0; i < scores.length; i++) {
            String[] j = strings[i];
            if (j.length == 1)
                objective.getScore(j[0]).setScore(scores[i]);
            else if (j.length == 3) {
                Team x = scoreboard.registerNewTeam(j[1]);
                x.addEntry(j[1]);
                x.setPrefix(j[0]);
                x.setSuffix(j[2]);
                objective.getScore(j[1]).setScore(scores[i]);
            } else if (j.length == 2) {
                Team x = scoreboard.registerNewTeam(j[0]);
                x.addEntry(j[0]);
                String[] str = prefixSuffixSplit(j[1]);
                x.setPrefix(str[0]);
                x.setSuffix(str[1]);
                objective.getScore(j[0]).setScore(scores[i]);
            }
        }
        return scoreboard;
    }

    public static String[] prefixSuffixSplit(String str) {
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
