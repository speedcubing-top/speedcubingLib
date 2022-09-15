package top.speedcubing.lib.api.event;

import java.util.UUID;

public class SkinRespondEvent {
    public final String name;
    public final UUID uuid;
    public final String value;
    public final String signature;

    public SkinRespondEvent(String name, UUID uuid, String value, String signature) {
        this.name = name;
        this.uuid = uuid;
        this.value = value;
        this.signature = signature;
    }
}
