package top.speedcubing.lib.api.event;

import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.eventbus.LibEventManager;

public class ProfileRespondEvent extends LibEventManager {
    public final ProfileSkin profile;

    public ProfileRespondEvent(ProfileSkin profile) {
        this.profile = profile;
    }
}