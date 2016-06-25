package com.silvlight.attr;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CDouble;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import static com.silvlight.attr.attrManager.getAttr;
import static com.silvlight.libs.findPlayer;

/**
 * Created by User on 2016-05-30.
 */

@api
public class attrFunc extends AbstractFunction {

    @Override
    public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {

        Player player = findPlayer(args[0].toString());
        if(player == null)
            throw new CREPlayerOfflineException("No player was specified!", t);

        Attribute attr = getAttr(args[2].toString());
        if(attr == null)
            throw new CRECastException(args[2]+" is not Attribute.", t);

        AttributeInstance instance = player.getAttribute(attr);

        if(args[1].toString().equalsIgnoreCase("set")){

            try{
                instance.setBaseValue(Double.valueOf(args[3].toString()));
            }catch(Exception e){
                throw new CRECastException("Expecting double, but recieved "+args[2].toString(), t);
            }
            return CVoid.VOID;

        }else if(args[1].toString().equalsIgnoreCase("get")){

            return CDouble.GetConstruct(instance.getValue());

        }else{

            throw new CRECastException("Excepting get or set, but recieved " + args[1].toString(), t);

        }
    }

    @Override
    public String getName() {
        return "nn_attr";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{ 4 };
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 0);
    }

    @Override
    public String docs() {
        return "void (UUID, set/get, attributeName, Value) Manage Attribute";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[]{CREPlayerOfflineException.class};
    }

    @Override
    public boolean isRestricted() {
        return false;
    }

    @Override
    public Boolean runAsync() {
        return null;
    }
}
