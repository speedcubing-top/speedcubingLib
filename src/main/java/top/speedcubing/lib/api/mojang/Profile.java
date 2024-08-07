package top.speedcubing.lib.api.mojang;

public class Profile {
    private final long time;
    private final String name;
    private final String uuid;

    public Profile(String name, String uuid) {
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
