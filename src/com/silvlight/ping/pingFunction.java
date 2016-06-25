package com.silvlight.ping;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigCompileException;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;

/**
 * Created by User on 2016-05-19.
 */

@api
public class pingFunction extends AbstractFunction {

    @Override
    public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {

        Object returned = null;
        Construct dummy = null;

        if(args.length == 2){
            returned = pingManager.cont(args[0].toString(), args[1].toString(), dummy, t);
        }else{
            pingManager.cont(args[0].toString(), args[1].toString(), args[2], t);
            return CVoid.VOID;
        }

        return CString.GetConstruct(returned);
    }

    @Override
    public String getName() {
        return "nn_ping";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{2, 3};
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 0);
    }

    @Override
    public String docs() {
        return "nn_ping [Type, Key, Value] Void Manage Ping";
    }

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

}