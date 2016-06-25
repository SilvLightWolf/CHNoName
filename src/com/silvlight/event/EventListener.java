package com.silvlight.event;

import com.laytonsmith.core.events.Driver;
import com.laytonsmith.core.events.EventUtils;
import com.silvlight.LifeCycle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

/**
 * Created by User on 2016-06-10.
 */
public class EventListener implements Listener {

    private static EventListener listener;

    public static void register(){
        if(listener == null){
            listener = new EventListener();
        }
        LifeCycle.chp.registerEvents(listener);
    }

    public static void unregister(){
        FurnaceBurnEvent.getHandlerList().unregister(listener);
        FurnaceSmeltEvent.getHandlerList().unregister(listener);
        FurnaceExtractEvent.getHandlerList().unregister(listener);
    }

    @EventHandler
    public void furnaceburnEvent(FurnaceBurnEvent event){
        furnaceBurn.furnace_burn_event evt = new furnaceBurn.furnace_burn_event(event);
        EventUtils.TriggerListener(Driver.EXTENSION, "furnace_burn", evt);
    }

    @EventHandler
    public void furnacesmeltEvent(FurnaceSmeltEvent event){
        furnaceSmelt.furnace_smelt_event evt = new furnaceSmelt.furnace_smelt_event(event);
        EventUtils.TriggerListener(Driver.EXTENSION, "furnace_smelt", evt);
    }

    @EventHandler
    public void furnaceextractEvent(FurnaceExtractEvent event){
        furnaceExtract.furnace_extract_event evt = new furnaceExtract.furnace_extract_event(event);
        EventUtils.TriggerListener(Driver.EXTENSION, "furnace_extract", evt);
    }

}