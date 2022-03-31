package cubing.lib.bukkit.packetwrapper;

import cubing.lib.utils.Reflections;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class OutPlayerListHeaderFooter {
    public static PacketPlayOutPlayerListHeaderFooter a(String header, String footer) {
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        Reflections.setField(packet, "a", new ChatComponentText(header));
        Reflections.setField(packet, "b", new ChatComponentText(footer));
        return packet;
    }
}
