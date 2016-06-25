package com.silvlight.gui;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by User on 2016-06-17.
 */

@api
public class AnvilFunc extends AbstractFunction{

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

        CraftPlayer player = ((CraftPlayer)Static.GetPlayer(args[0].toString(), t).getHandle());
        CClosure callback = (CClosure) args[1];

        AnvilGUI gui = new AnvilGUI(player, new AnvilGUI.AnvilClickEventHandler(){
            @Override
            public void onAnvilClick(AnvilGUI.AnvilClickEvent event){
                if(event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT){
                    event.setWillClose(true);
                    event.setWillDestroy(true);

                    callback.execute(new CString(event.getName(), t));
                }else{
                    event.setWillClose(false);
                    event.setWillDestroy(false);
                }
            }
        });

        ItemStack difitem;
        if(args.length == 3){
            difitem = (ItemStack) ObjectGenerator.GetGenerator().item(args[2], t).getHandle();
        }else{
            difitem = new ItemStack(Material.NAME_TAG);
        }

        gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, difitem);

        gui.open();

        return CVoid.VOID;
    }

    @Override
    public String getName() {
        return "nn_anvil";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{ 2, 3 };
    }

    @Override
    public String docs() {
        return "VOID (player, callback[, leftitem]) Anvil GUI";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 2, 0);
    }
}
