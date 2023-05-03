package top.speedcubing.lib.api.mojang;

import top.speedcubing.lib.utils.UUIDUtils;

import java.util.UUID;

public class Profile {
    final long time;
    final String name;
    final String uuidString;
    UUID uuid;

    public Profile(String name, String uuidString) {
        this.time = System.currentTimeMillis();
        this.name = name;
        this.uuidString = uuidString;
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        if (uuid == null)
            uuid = UUID.fromString(uuidString);
        return uuid;
    }

    public String getUUIDString() {
        return uuidString;
    }

    public String getUndashedUUID() {
        return UUIDUtils.unDash(uuidString);
    }

    public long getTimeMillis() {
        return time;
    }
}
