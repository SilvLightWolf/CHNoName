package com.silvlight.gui;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import net.minecraft.server.v1_9_R2.ChatMessage;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.PacketPlayOutOpenWindow;
import net.minecraft.server.v1_9_R2.PacketPlayOutSetSlot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

/**
 * Created by User on 2016-06-19.
 */

@api
public class HopperFunc extends AbstractFunction{

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[0];
    }

    @Override
    public boolean isRestricted() {
        return false;
    }

    @Override
    public Boolean runAsync() {
        return null;
    }

    @Override
    public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {

        Player player = ((BukkitMCPlayer) Static.GetPlayer(args[0], t))._Player();
        EntityPlayer p = ((CraftPlayer)player).getHandle();
        int c = p.nextContainerCounter();

        PacketPlayOutOpenWindow hopper = new PacketPlayOutOpenWindow(
                c,
                "minecraft:hopper",
                new ChatMessage(args[1].toString()),
                5
        );
        p.playerConnection.sendPacket(hopper);


        if(args.length == 3){
            CArray arr = (CArray) args[2];
            sendItem(p, c, arr, t);
        }

        return CVoid.VOID;
    }

    @Override
    public String getName() {
        return "nn_hopper";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[] { 2, 3 };
    }

    @Override
    public String docs() {
        return "void (player, title[, invarray]) Hopper Array";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 2, 0);
    }

    public void sendItem(EntityPlayer p, int id, CArray items, Target t){

        int size = Integer.parseInt(items.size()+"");
        for(int i = 0; i < 5 ; i++){
            if(items.get(i, t) == null)
                continue;
            ItemStack item = ((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(items.get(i, t), t)).__ItemStack();
            Bukkit.broadcastMessage(i+ "번째 아이템 등록");
            PacketPlayOutSetSlot packet = new PacketPlayOutSetSlot(
                    id,
                    i,
                    CraftItemStack.asNMSCopy(item)
            );
            p.playerConnection.sendPacket(packet);
        }

    }
}
