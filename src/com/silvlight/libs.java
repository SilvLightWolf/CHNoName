package com.silvlight;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Created by User on 2016-05-30.
 */
public class libs {

    public static Player findPlayer(String UUID) {

        for (Player player : Bukkit.getOnlinePlayers()){
            if(player.getUniqueId().toString().equalsIgnoreCase(UUID)){
                return player;
            }

        }
        return null;
    }

    public static Entity findEntity(String UUID){

        for(World world : Bukkit.getWorlds()){

            for(Entity entity : world.getEntities()){

                if(entity.getUniqueId().toString().equalsIgnoreCase(UUID)){

                    return entity;
                }

            }

        }

        return null;
    }
}