package top.speedcubing.lib.api.mojang;

import java.util.UUID;

public class ProfileNameUUID {
    final String name;
    final String uuidString;
    UUID uuid;

    public ProfileNameUUID(String name, String uuidString) {
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
}
