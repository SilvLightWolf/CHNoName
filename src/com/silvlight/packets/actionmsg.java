package com.silvlight.packets;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;

/**
 * Created by User on 2016-06-09.
 */

@api
public class actionmsg extends AbstractFunction {

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[]{CREPlayerOfflineException.class };
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

        String message = args[1].getValue();

        CraftPlayer p = (CraftPlayer) Static.GetPlayer(args[0], t).getHandle();

        IChatBaseComponent txt = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat action = new PacketPlayOutChat(txt, (byte) 2);
        p.getHandle().playerConnection.sendPacket(action);

        return CVoid.VOID;
    }

    @Override
    public String getName() {
        return "nn_actmsg";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{ 2 };
    }

    @Override
    public String docs() {
        return "void (Player, Message) Send Action Msg";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 3);
    }
}
