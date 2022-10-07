package top.speedcubing.lib.api.mojang;

import java.util.UUID;

public class Profile {
    final String name;
    final String uuidString;
    final String value;
    final String signature;
    UUID uuid;

    public Profile(String name, String uuidString, String value, String signature) {
        this.name = name;
        this.uuidString = uuidString;
        this.value = value;
        this.signature = signature;
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

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
