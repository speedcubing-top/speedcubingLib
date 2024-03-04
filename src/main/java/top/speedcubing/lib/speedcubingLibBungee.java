package top.speedcubing.lib;

import net.md_5.bungee.api.plugin.Plugin;

public class speedcubingLibBungee extends Plugin {
    public void onEnable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
    }
}