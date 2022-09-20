package top.speedcubing.lib.api.event;

import top.speedcubing.lib.eventbus.LibEventManager;

import java.util.UUID;

public class ProfileRespondEvent extends LibEventManager {
    public final String name;
    public final UUID uuid;

    public ProfileRespondEvent(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
