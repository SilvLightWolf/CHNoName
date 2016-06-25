package com.silvlight.packets;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;

import static com.silvlight.packets.lib.allsendPacket;

/**
 * Created by User on 2016-06-09.
 */

@api
public class particle extends AbstractFunction {

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[]{ CRECastException.class };
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

        EnumParticle part = EnumParticle.valueOf(args[0].toString());
        float x = Float.parseFloat(args[1].toString());
        float y = Float.parseFloat(args[2].toString());
        float z = Float.parseFloat(args[3].toString());
        float xof = Float.parseFloat(args[4].toString());
        float yof = Float.parseFloat(args[5].toString());
        float zof = Float.parseFloat(args[6].toString());
        float speed = Float.parseFloat(args[7].toString());
        int ptcs = Integer.parseInt(args[8].toString());

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                part,
                true,
                x,
                y,
                z,
                xof,
                yof,
                zof,
                speed,
                ptcs
        );

        allsendPacket(packet);

        return CVoid.VOID;
    }
    @Override
    public String getName() {
        return "nn_particle";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{ 9 };
    }

    @Override
    public String docs() {
        return "void (Particle, x, y, z, xoffset, yoffset, zoffset, speed, particles) Particle";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 0);
    }
}
