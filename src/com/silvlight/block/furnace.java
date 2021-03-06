package com.silvlight.block;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCWorld;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import org.bukkit.Location;
import org.bukkit.block.Furnace;

/**
 * Created by User on 2016-06-25.
 */

public class furnace{

    @api
    public static class getFurnace extends AbstractFunction{

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends CREThrowable>[] thrown() {
            return new Class[]{};
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

            Location loc = (Location) ((BukkitMCLocation) ObjectGenerator.GetGenerator().location(args[0], Static.getWorld(args[1], t), t)).getHandle();
            if (loc.getBlock().getState() instanceof Furnace) {

                Furnace furnace = (Furnace) loc.getBlock().getState();
                CArray arr = CArray.GetAssociativeArray(t);

                MCItemStack fuel = new BukkitMCItemStack(furnace.getInventory().getFuel());
                MCItemStack result = new BukkitMCItemStack(furnace.getInventory().getResult());
                MCItemStack smelting = new BukkitMCItemStack(furnace.getInventory().getSmelting());

                arr.set("cooktime", String.valueOf(furnace.getCookTime()));
                arr.set("burntime", String.valueOf(furnace.getBurnTime()));
                arr.set("fuel", ObjectGenerator.GetGenerator().item(fuel, t), t);
                arr.set("result", ObjectGenerator.GetGenerator().item(result, t), t);
                arr.set("smelting", ObjectGenerator.GetGenerator().item(smelting, t), t);

                return arr;
            } else {
                throw new CRECastException("Expect Furnace, But recieved " + loc.getBlock().getState().getType(), t);
            }

        }

        @Override
        public String getName() {
            return "nn_get_furnace";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{2};
        }

        @Override
        public String docs() {
            return "Array (LocationArray, world) return to location furnace blocks infos";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 2, 0);
        }
    }

    @api
    public static class setFurnace extends AbstractFunction{

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends CREThrowable>[] thrown() {
            return new Class[]{};
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

            Location loc = (Location) ((BukkitMCLocation) ObjectGenerator.GetGenerator().location(args[0], Static.getWorld(args[1], t), t)).getHandle();
            if (loc.getBlock().getState() instanceof Furnace) {

                Furnace furnace = (Furnace) loc.getBlock().getState();
                String key = args[2].toString();
                if(key.equalsIgnoreCase("burntime")){
                    furnace.setBurnTime(Short.valueOf(args[3].toString()));
                }else if(key.equalsIgnoreCase("cooktime")){
                    furnace.setCookTime(Short.valueOf(args[3].toString()));
                }else if(key.equalsIgnoreCase("fuel")){
                    furnace.getInventory().setFuel(((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(args[3], t)).asItemStack());
                }else if(key.equalsIgnoreCase("result")){
                    furnace.getInventory().setResult(((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(args[3], t)).asItemStack());
                }else if(key.equalsIgnoreCase("smelting")){
                    furnace.getInventory().setSmelting(((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(args[3], t)).asItemStack());
                }else{
                    throw new CRECastException("Unknown Key. keys : burntime, cooktime, fuel, result, smelting", t);
                }
            } else {
                throw new CRECastException("Expect Furnace, But recieved " + loc.getBlock().getState().getType(), t);
            }

            return CVoid.VOID;
        }

        @Override
        public String getName() {
            return "nn_set_furnace";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{ 4 };
        }

        @Override
        public String docs() {
            return "void (LocationArray, world, key, val) key : burntime, cooktime, fuel, result, smelting";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 2, 0);
        }
    }
}
