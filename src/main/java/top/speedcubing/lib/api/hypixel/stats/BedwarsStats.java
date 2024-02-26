package top.speedcubing.lib.api.hypixel.stats;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BedwarsStats {
    private final JsonObject object;
    private final int experience;
    private final int level;
    private final int progress;
    private final int nextLevel;
    private final char displayChar;
    public int getLevel() {
        return level;
    }

    public int getProgress(){
        return progress;
    }

    public int getNextLevel(){
        return nextLevel;
    }

    public int getExperience() {
        return experience;
    }

    public char getDisplayChar(){
        return displayChar;
    }

    public JsonElement get(String s) {
        return object.get(s);
    }

    public JsonObject getJson() {
        return object;
    }

    public BedwarsStats(JsonObject object) {
        this.object = object;
        this.experience = object.get("Experience").getAsInt();
        int frontXp = 0;
        int lowxp = 0;
        int nextLevel = 0;
        int progress = 0;
        for (int i : new int[]{500, 1000, 2000, 3500}) {
            frontXp += i;
            int a = experience % 487000 - frontXp;
            if (a < 0) {
                nextLevel = i;
                progress = a + i;
                break;
            } else if (frontXp == 7000) {
                nextLevel = 5000;
                progress = a % 5000;
                lowxp = a / 5000 + 4;
                break;
            }
            lowxp += 1;
        }
        this.level = 100 * (experience / 487000) + lowxp;
        this.progress = progress;
        this.nextLevel = nextLevel;
        this.displayChar = level < 1100 ? '✫' : level < 2100 ? '✪' : '⚝';
    }
}
