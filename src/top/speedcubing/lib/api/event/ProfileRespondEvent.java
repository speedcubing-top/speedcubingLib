package top.speedcubing.lib.api.event;

import java.util.UUID;

public class ProfileRespondEvent {
    public final String name;
    public final UUID uuid;

    public ProfileRespondEvent(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
