package com.silvlight.ping;

import io.netty.channel.Channel;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.ServerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 2016-05-05.
 */
public class pingInjector implements Listener {

    private MinecraftServer server;
    private List<?> networkManagers;

    public pingInjector() {
        try {
            CraftServer craftserver = (CraftServer) Bukkit.getServer();
            Field console = craftserver.getClass().getDeclaredField("console");
            console.setAccessible(true);
            this.server = (MinecraftServer) console.get(craftserver);
            ServerConnection conn = this.server.getServerConnection();
            networkManagers = Collections.synchronizedList((List<?>) this.getNetworkManagerList(conn));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void injectOpenConnections() {
        try {
            Field field = Reflection.getField(NetworkManager.class, "channel");
            field.setAccessible(true);
            for (Object manager : networkManagers) {
                Channel channel = (Channel) field.get(manager);
                if (channel.pipeline().context("VInjector") == null && (channel.pipeline().context("packet_handler") != null)) {
                    channel.pipeline().addBefore("packet_handler", "VInjector", new PacketHandler());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getNetworkManagerList(ServerConnection conn) {
        try {
            for (Method method : conn.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.getReturnType() == List.class) {
                    Object object = method.invoke(null, conn);
                    return object;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @EventHandler
    public void serverListPing(ServerListPingEvent event) {
        this.injectOpenConnections();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.injectOpenConnections();
    }
}