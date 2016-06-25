package com.silvlight.event;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCEnchantment;
import com.laytonsmith.abstraction.MCItemMeta;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.MCMaterialData;
import com.laytonsmith.abstraction.blocks.MCMaterial;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2016-06-11.
 */


public class furnaceSmelt {

    public interface furnace_smelt_event_interface extends BindableEvent {

        public MCItemStack getResult();
        public MCItemStack getSource();
        public void setResult(MCItemStack result);

    }

    public static class furnace_smelt_event implements  furnace_smelt_event_interface{
        FurnaceSmeltEvent e;

        public furnace_smelt_event(Event e){
            this.e = (FurnaceSmeltEvent) e;
        }

        @Override
        public Object _GetObject() {
            return this.e;
        }

        @Override
        public MCItemStack getResult() {
            return new BukkitMCItemStack(e.getResult());
        }

        @Override
        public MCItemStack getSource() {
            return new BukkitMCItemStack(e.getSource());
        }

        @Override
        public void setResult(MCItemStack result) {
            e.setResult(((BukkitMCItemStack)result).asItemStack());
        }
    }

    @api
    public static class furnace_smelt_event_api extends AbstractEvent {

        @Override
        public String getName() {
            return "furnace_smelt";
        }

        @Override
        public String docs() {
            return "";
        }

        @Override
        public Version since() {
            return new SimpleVersion(1, 1, 0);
        }

        @Override
        public boolean matches(Map<String, Construct> map, BindableEvent bindableEvent) throws PrefilterNonMatchException {
            return true;
        }

        @Override
        public BindableEvent convert(CArray cArray, Target target) {
            return null;
        }

        @Override
        public Map<String, Construct> evaluate(BindableEvent e) throws EventException {
            Map<String, Construct> returnvar = new HashMap<String, Construct>();

            if( e instanceof furnace_smelt_event_interface ){

                furnace_smelt_event_interface evt = (furnace_smelt_event_interface) e;

                Construct result = ObjectGenerator.GetGenerator().item(evt.getResult(), Target.UNKNOWN);
                Construct source = ObjectGenerator.GetGenerator().item(evt.getSource(), Target.UNKNOWN);

                returnvar.put("result", result);
                returnvar.put("source", source);

            }

            return returnvar;
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent e) {
            furnace_smelt_event_interface evt = (furnace_smelt_event_interface) e;

            if(key.equalsIgnoreCase("result")){
                evt.setResult(ObjectGenerator.GetGenerator().item(value, Target.UNKNOWN));
                return true;
            }

            return false;
        }
    }

}
