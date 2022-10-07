package top.speedcubing.lib.api.mojang;

public class ProfileSkin {
    final String value;
    final String signature;

    public ProfileSkin(String value, String signature) {
        this.value = value;
        this.signature = signature;
    }

    public String getValue(){
        return value;
    }

    public String getSignature(){
        return signature;
    }
}
