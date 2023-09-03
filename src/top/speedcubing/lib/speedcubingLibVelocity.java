package top.speedcubing.lib;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "speedcubinglib")
public class speedcubingLibVelocity {

    public static ProxyServer server;
    public static Logger logger;
    public static CommandManager commandManager;
    public static Path folder;

    @Inject
    private speedcubingLibVelocity(ProxyServer server, CommandManager commandManager, Logger logger, @DataDirectory final Path folder) {
        speedcubingLibVelocity.server = server;
        speedcubingLibVelocity.commandManager = commandManager;
        speedcubingLibVelocity.logger = logger;
        speedcubingLibVelocity.folder = folder;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        }
    }
}
