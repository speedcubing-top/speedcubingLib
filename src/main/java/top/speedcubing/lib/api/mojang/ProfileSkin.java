package top.speedcubing.lib.api.mojang;

public class ProfileSkin extends Profile {
    private final Skin skin;

    public ProfileSkin(String name, String uuidString, String value, String signature) {
        super(name, uuidString);
        this.skin = new Skin(value, signature);
    }

    public Skin getSkin() {
        return this.skin;
    }

}
