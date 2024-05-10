package top.speedcubing.lib.bukkit.events.packet;

import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.eventbus.CubingEvent;

public class ProfileRespondEvent extends CubingEvent {

    private final ProfileSkin profile;
    public ProfileRespondEvent(ProfileSkin profile) {
        this.profile = profile;
    }
    public ProfileSkin getProfile() {
        return profile;
    }
}