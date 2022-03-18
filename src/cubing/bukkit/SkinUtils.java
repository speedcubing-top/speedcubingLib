package cubing.bukkit;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkinUtils {
    public static String[] getProfileSkin(GameProfile profile) {
        Property property = profile.getProperties().get("textures").iterator().next();
        return new String[]{property.getValue(), property.getSignature()};
    }
}
