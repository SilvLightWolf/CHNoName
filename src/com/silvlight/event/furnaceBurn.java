package com.silvlight.event;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.abstraction.Implementation;
import com.laytonsmith.abstraction.MCItemStack;
import com.laytonsmith.abstraction.bukkit.BukkitMCItemStack;
import com.laytonsmith.annotations.abstraction;
import com.laytonsmith.annotations.api;
import com.laytonsmith.annotations.event;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.events.AbstractEvent;
import com.laytonsmith.core.events.BindableEvent;
import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.exceptions.EventException;
import com.laytonsmith.core.exceptions.PrefilterNonMatchException;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.FurnaceBurnEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2016-06-10.
 */
public class furnaceBurn {

    public interface furnace_burn_event_interface extends BindableEvent{

        public int getBurnTime();
        public MCItemStack getFuel();
        public void setBurning(boolean burning);
        public void setBurnTime(int time);

    }

    @abstraction(type = Implementation.Type.BUKKIT)
    public static class furnace_burn_event implements furnace_burn_event_interface{
        FurnaceBurnEvent e;

        public furnace_burn_event(Event e){
            this.e = (FurnaceBurnEvent) e;
        }

        @Override
        public int getBurnTime(){
            return e.getBurnTime();
        }

        @Override
        public MCItemStack getFuel(){
            return new BukkitMCItemStack(e.getFuel());
        }

        @Override
        public void setBurning(boolean burning){
            e.setBurning(burning);
        }

        @Override
        public void setBurnTime(int time){
            e.setBurnTime(time);
        }

        @Override
        public Object _GetObject() {
            return this.e;
        }
    }

    @api
    public static class furnace_burn_event_api extends AbstractEvent {

        @Override
        public String getName() {
            return "furnace_burn";
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
            if(e instanceof furnace_burn_event_interface){

                furnace_burn_event_interface evt = ((furnace_burn_event_interface) e);

                CInt burntime = new CInt(evt.getBurnTime(), Target.UNKNOWN);
                Construct fuel = ObjectGenerator.GetGenerator().item(evt.getFuel(), Target.UNKNOWN);

                returnvar.put("burntime", burntime);
                returnvar.put("fuel", fuel);

            }

            return returnvar;
        }

        @Override
        public Driver driver() {
            return Driver.EXTENSION;
        }

        @Override
        public boolean modifyEvent(String key, Construct value, BindableEvent e) {
            furnace_burn_event_interface evt = ((furnace_burn_event_interface) e);

            if(key.equalsIgnoreCase("burntime") && value instanceof CInt) {
                evt.setBurnTime(Integer.parseInt(value.val()));
                return true;
            }else if(key.equalsIgnoreCase("burning") && value instanceof CBoolean){
                evt.setBurning(Boolean.valueOf(value.toString()));
                return true;
            }

            return false;
        }
    }
}
