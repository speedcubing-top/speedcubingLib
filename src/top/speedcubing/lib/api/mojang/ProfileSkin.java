package top.speedcubing.lib.api.mojang;

public class ProfileSkin extends Profile {
    final String value;
    final String signature;

    public ProfileSkin(String name, String uuidString, String value, String signature) {
        super(name, uuidString);
        this.value = value;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }
}
