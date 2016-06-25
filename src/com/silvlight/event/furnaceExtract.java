package com.silvlight.event;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.abstraction.blocks.MCMaterial;
import com.laytonsmith.abstraction.bukkit.blocks.BukkitMCMaterial;
import com.laytonsmith.abstraction.bukkit.entities.BukkitMCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceExtractEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2016-06-11.
 */
public class furnaceExtract {

    public interface furnace_extract_event_interface extends BindableEvent {

        public int getItemAmount();
        public MCMaterial getItemType();
        public MCPlayer getPlayer();

    }

    public static class furnace_extract_event implements furnace_extract_event_interface{

        FurnaceExtractEvent e;

        public furnace_extract_event(Event e){
            this.e = (FurnaceExtractEvent) e;
        }

        @Override
        public Object _GetObject() {
            return this.e;
        }

        @Override
        public int getItemAmount() {
            return e.getItemAmount();
        }

        @Override
        public MCMaterial getItemType() {
            return new BukkitMCMaterial(e.getItemType());
        }

        @Override
        public MCPlayer getPlayer() {
            return new BukkitMCPlayer(e.getPlayer());
        }
    }

    @api
    public static class furnace_extract_event_api extends AbstractEvent {

        @Override
        public String getName() {
            return "furnace_extract";
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

            if( e instanceof furnace_extract_event_interface ){

                furnace_extract_event_interface evt = (furnace_extract_event_interface) e;

                CInt amount = new CInt(evt.getItemAmount(), Target.UNKNOWN);
                MCMaterial material = evt.getItemType();
                MCPlayer player = evt.getPlayer();

                returnvar.put("player", new CString(player.getUniqueID().toString(), Target.UNKNOWN));
                returnvar.put("material", new CString(material.toString(), Target.UNKNOWN));
                returnvar.put("amount", amount);

            }

            return returnvar;
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String s, Construct construct, BindableEvent bindableEvent) {
            return false;
        }
    }
}
