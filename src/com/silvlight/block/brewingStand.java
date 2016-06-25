package com.silvlight.block;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCItemStack;
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
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;

/**
 * Created by User on 2016-06-25.
 */
public class brewingStand {

    @api
    public static class getBrewingStand extends AbstractFunction {

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

            if (loc.getBlock().getState() instanceof BrewingStand) {

                BrewingStand brew = (BrewingStand) loc.getBlock().getState();
                CArray arr = CArray.GetAssociativeArray(t);

                MCItemStack fuel = new BukkitMCItemStack(brew.getInventory().getFuel());
                MCItemStack ingred = new BukkitMCItemStack(brew.getInventory().getIngredient());

                arr.set("fuellevel", String.valueOf(brew.getFuelLevel()));
                arr.set("brewingtime", String.valueOf(brew.getBrewingTime()));
                arr.set("fuel", ObjectGenerator.GetGenerator().item(fuel, t), t);
                arr.set("ingredient", ObjectGenerator.GetGenerator().item(ingred, t), t);

                return arr;
            } else {
                throw new CRECastException("Expect BREWING STAND, But recieved " + loc.getBlock().getState().getType(), t);
            }
        }

        @Override
        public String getName() {
            return "nn_get_brew";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{ 2 };
        }

        @Override
        public String docs() {
            return "Array (LocationArray, world) return to Brewing Stand info";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 2, 0);
        }
    }

    @api
    public static class setBrewingStand extends AbstractFunction{

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
            Location loc = (Location) ((BukkitMCLocation) ObjectGenerator.GetGenerator().location(args[0], Static.getWorld(args[1], t), t)).getHandle();
            if (loc.getBlock().getState() instanceof BrewingStand) {
                BrewingStand brew = (BrewingStand) loc.getBlock().getState();
                String key = args[2].toString();
                if(key.equalsIgnoreCase("brewingtime")){
                    brew.setBrewingTime(Integer.valueOf(args[3].toString()));
                }else if(key.equalsIgnoreCase("fuellevel")){
                    brew.setFuelLevel(Integer.valueOf(args[3].toString()));
                }else if(key.equalsIgnoreCase("fuel")){
                    brew.getInventory().setFuel(((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(args[3], t)).asItemStack());
                }else if(key.equalsIgnoreCase("ingredient")){
                    brew.getInventory().setIngredient(((BukkitMCItemStack)ObjectGenerator.GetGenerator().item(args[3], t)).asItemStack());
                }else{
                    throw new CRECastException("Unknown Key. keys : brewingtime, fuellevel, fuel, ingredient", t);
                }
            } else {
                throw new CRECastException("Expect BREWING STAND, But recieved " + loc.getBlock().getState().getType(), t);
            }

            return CVoid.VOID;
        }

        @Override
        public String getName() {
            return "nn_set_brew";
        }

        @Override
        public Integer[] numArgs() {
            return new Integer[]{ 4 };
        }

        @Override
        public String docs() {
            return null;
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 2, 0);
        }
    }
}
