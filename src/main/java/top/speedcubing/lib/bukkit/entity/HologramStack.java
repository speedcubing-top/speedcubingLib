package top.speedcubing.lib.bukkit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import top.speedcubing.lib.bukkit.events.entity.ClickEvent;

public class HologramStack {
    private final int count;
    private final double d;

    public final List<Hologram> hologramList = new ArrayList<>();

    public HologramStack(int count, double d, boolean everyoneCanSee, boolean autoSpawn, String... name) {
        this.count = count;
        this.d = d;
        for (int i = 0; i < count; i++) {
            hologramList.add(new Hologram(name[i], everyoneCanSee, autoSpawn));
        }
    }

    public void delete() {
        for (Hologram h : hologramList) {
            h.delete();
        }
    }

    public Hologram get(int i) {
        return hologramList.get(i);
    }

    public HologramStack setClickEvent(Consumer<ClickEvent> e) {
        for (Hologram h : hologramList) {
            h.setClickEvent(e);
        }
        return this;
    }

    public HologramStack addListener(Player... players) {
        for (Hologram h : hologramList) {
            h.addListener(players);
        }
        return this;
    }

    public HologramStack addListener(Collection<Player> players) {
        for (Hologram h : hologramList) {
            h.addListener(players);
        }
        return this;
    }

    public HologramStack setListener(Player... players) {
        for (Hologram h : hologramList) {
            h.setListener(players);
        }
        return this;
    }

    public HologramStack setListener(Collection<Player> players) {
        for (Hologram h : hologramList) {
            h.setListener(players);
        }
        return this;
    }

    public HologramStack removeListener(Player... players) {
        for (Hologram h : hologramList) {
            h.removeListener(players);
        }
        return this;
    }

    public HologramStack undoSetListener() {
        for (Hologram h : hologramList) {
            h.undoSetListener();
        }
        return this;
    }

    public HologramStack world(String... world) {
        for (Hologram h : hologramList) {
            h.world(world);
        }
        return this;
    }


    public HologramStack setLocation(double x, double y, double z, float yaw, float pitch) {
        for (int i = 0; i < count; i++) {
            hologramList.get(i).setLocation(x, y + i * d, z, yaw, pitch);
        }
        return this;
    }

    public HologramStack setLocation(Location location) {
        for (int i = 0; i < count; i++) {
            location.setY(location.getY() + i * d);
            hologramList.get(i).setLocation(location);
        }
        return this;
    }

    public HologramStack spawn() {
        for (Hologram h : hologramList) {
            h.spawn();
        }
        return this;
    }

    public HologramStack despawn() {
        for (Hologram h : hologramList) {
            h.despawn();
        }
        return this;
    }


    public HologramStack setName(int index, String name) {
        hologramList.get(index).setName(name);
        return this;
    }
}
