package top.speedcubing.lib.api.mojang;

public class Profile {
    final long time;
    final String name;
    final String uuid;

    public  Profile(String name, String uuid) {
        this.time = System.currentTimeMillis();
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public long getTimeMillis() {
        return time;
    }
}
