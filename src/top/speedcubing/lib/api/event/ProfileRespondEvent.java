package top.speedcubing.lib.api.event;

import top.speedcubing.lib.api.mojang.ProfileSkin;
import top.speedcubing.lib.eventbus.*;

public class ProfileRespondEvent extends CubingEvent {
    public final ProfileSkin profile;

    public ProfileRespondEvent(ProfileSkin profile) {
        this.profile = profile;
    }
}