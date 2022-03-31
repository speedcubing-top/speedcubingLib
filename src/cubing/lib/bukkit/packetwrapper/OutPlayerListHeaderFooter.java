package cubing.lib.bukkit.packetwrapper;

import cubing.lib.utils.Reflections;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class OutPlayerListHeaderFooter {
    public static PacketPlayOutPlayerListHeaderFooter a(String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Reflections.setField(packet, "a", new TextComponent(header));
        Reflections.setField(packet, "b",new TextComponent(footer));
        return packet;
    }
}
