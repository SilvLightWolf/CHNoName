package com.silvlight.packets;

import net.minecraft.server.v1_9_R2.Packet;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by User on 2016-06-03.
 */
public class lib {

    public static void allsendPacket(Packet packet){

        for(Player p :Bukkit.getOnlinePlayers()){
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }

    }

}
