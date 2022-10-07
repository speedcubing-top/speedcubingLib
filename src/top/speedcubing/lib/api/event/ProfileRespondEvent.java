package top.speedcubing.lib.api.event;

import top.speedcubing.lib.api.mojang.Profile;
import top.speedcubing.lib.eventbus.LibEventManager;

public class ProfileRespondEvent extends LibEventManager {
    public final Profile profile;

    public ProfileRespondEvent(Profile profile) {
        this.profile = profile;
    }
}