package com.silvlight.attr;

import org.bukkit.attribute.Attribute;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by User on 2016-05-30.
 */
public class attrManager extends JavaPlugin {

    public static Attribute getAttr(String name){

        //Attributes : G_ARMOR, G_ATTACK_DAMAGE, G_ATTACK_SPEED, G_FOLLOW_RANGE, G_KNOCKBACK_RESISTANCE, G_LUCK, G_MAX_HEALTH, G_MOVEMENT_SPEED

        if(name.equalsIgnoreCase("ARMOR")){

            return Attribute.GENERIC_ARMOR;

        }else if(name.equalsIgnoreCase("ATTACK_DAMAGE")){

            return Attribute.GENERIC_ATTACK_DAMAGE;

        }else if(name.equalsIgnoreCase("ATTACK_SPEED")){

            return Attribute.GENERIC_ATTACK_SPEED;

        }else if(name.equalsIgnoreCase("FOLLOW_RANGE")){

            return Attribute.GENERIC_FOLLOW_RANGE;

        }else if(name.equalsIgnoreCase("KNOCKBACK_RESISTANCE")){

            return Attribute.GENERIC_KNOCKBACK_RESISTANCE;

        }else if(name.equalsIgnoreCase("LUCK")){

            return Attribute.GENERIC_LUCK;

        }else if(name.equalsIgnoreCase("MAX_HEALTH")){

            return Attribute.GENERIC_MAX_HEALTH;

        }else if(name.equalsIgnoreCase("MOVEMENT_SPEED")){

            return Attribute.GENERIC_MOVEMENT_SPEED;

        }else{

            return null;

        }
    }

}