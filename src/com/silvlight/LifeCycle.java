package com.silvlight;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

import com.silvlight.event.EventListener;
import com.silvlight.ping.pingInjector;
import com.silvlight.ping.pingManager;
import org.bukkit.event.Listener;

/**
 * Created by User on 2016-05-30.
 */

@MSExtension("CHNoName")
public class LifeCycle extends AbstractExtension implements Listener{

    public static CommandHelperPlugin chp;

    @Override
    public Version getVersion() {
        return new SimpleVersion(1, 2, 0);
    }

    public void onStartup(){

        chp = CommandHelperPlugin.self;
        EventListener.register();
        CommandHelperPlugin.self.registerEvents(pingManager.injector = new pingInjector());
        System.out.println("CHNoName "+getVersion()+" has Sucessfully been enabled!");

    }

    public void onShutdown(){

        System.out.println("CHNoName "+getVersion()+ " has Sucessfully been disable!");
        EventListener.unregister();

    }


}