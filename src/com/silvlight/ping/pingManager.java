package com.silvlight.ping;

import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.CRE.CRECastException;
import com.laytonsmith.core.exceptions.CRE.CREIllegalArgumentException;
import com.laytonsmith.core.exceptions.ConfigCompileException;
import com.silvlight.LifeCycle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.NetworkManager;
import net.minecraft.server.v1_9_R2.ServerConnection;
import net.minecraft.server.v1_9_R2.ServerPing;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SourceCode By Vinetos : Youtube : https://www.youtube.com/channel/UCSklSjOxtTFw6R_mmaAd61w
 * create by SLW - 2016.05.20
 */
public class pingManager extends JavaPlugin implements Listener{

    public static pingInjector injector;
    public static int curplayers = 0;
    public static int maxplayers = 0;
    public static String version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];

    public static Object cont(String type, String key, Construct val, Target t){
        switch(type){
            case "current":
                if(key.equalsIgnoreCase("set")){
                    try {
                        curplayers = Integer.parseInt(String.valueOf(val));
                        return CVoid.VOID;
                    }catch(Exception e){
                        throw new CRECastException("Expecting an integer, but recieved \" "+val.toString()+" \" instead.", t);
                    }
                }else if(key.equalsIgnoreCase("get")){
                    return curplayers;
                }else{
                    throw new CRECastException("Incorrect number of arguments passed to nn_ping()", t);
                }
            case "max":
                if(key.equalsIgnoreCase("set")){
                    try {
                        maxplayers = Integer.parseInt(String.valueOf(val));
                        return CVoid.VOID;
                    }catch(Exception e){
                        throw new CRECastException("Expecting an integer, but recieved \" "+val.toString()+" \" instead.", t);
                    }
                }else if(key.equalsIgnoreCase("get")){
                    return maxplayers;
                }else{
                    throw new CRECastException("Incorrect number of arguments passed to nn_ping()", t);
                }
            case "version":
                if(key.equalsIgnoreCase("set")){
                    try {
                        version = val.toString();
                        return CVoid.VOID;
                    }catch(Exception e){
                        throw new CRECastException("Expecting an integer, but recieved \" "+val.toString()+" \" instead.", t);
                    }
                }else if(key.equalsIgnoreCase("get")){
                    return version;
                }else{
                    throw new CRECastException("Incorrect number of arguments passed to nn_ping()", t);
                }
            default:
                throw new CRECastException("Incorrect number of arguments passed to nn_ping()", t);
        }
    }

}

class PacketHandler extends ChannelDuplexHandler {

    @Override
    public void write(ChannelHandlerContext ctx, Object m, ChannelPromise promise) throws Exception {

        if(m.getClass().getSimpleName().equalsIgnoreCase("PacketStatusOutServerInfo")){

            ServerPing sp = (ServerPing) Reflection.getFieldValue(m, "b");

            sp.setPlayerSample(new ServerPing.ServerPingPlayerSample(pingManager.maxplayers, pingManager.curplayers));
            sp.setServerInfo(new ServerPing.ServerData(pingManager.version, sp.getServerData().getProtocolVersion()));

        }
        super.write(ctx, m, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
        super.channelRead(c, m);
    }

}


class Reflection {public static Class<?> getClass(String classname) {
    try {
        String version = getNmsVersion();
        String path = classname.replace("{nms}", "net.minecraft.server."+version)
                .replace("{nm}", "net.minecraft."+version)
                .replace("{cb}", "org.bukkit.craftbukkit.."+version);
        return Class.forName(path);
    } catch (Throwable t) {
        t.printStackTrace();
        return null;
    }
}

    public static Class[] getArrayClass(String classname, int arraySize) {
        try {
            String version = getNmsVersion();
            String path = classname.replace("{nms}", "net.minecraft.server."+version)
                    .replace("{nm}", "net.minecraft."+version)
                    .replace("{cb}", "org.bukkit.craftbukkit.."+version);
            return new Class[]{ Array.newInstance(getClass(classname), arraySize).getClass() };
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static String getNmsVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Object getNmsPlayer(Player p) throws Exception{
        Method getHandle = p.getClass().getMethod("getHandle");
        return getHandle.invoke(p);
    }

    public static Object getNmsScoreboard(Scoreboard s) throws Exception {
        Method getHandle = s.getClass().getMethod("getHandle");
        return getHandle.invoke(s);
    }

    public static Object getFieldValue(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static ArrayList<Field> getFields(Object instance, Class<?> fieldType) throws Exception {
        Field[] fields = instance.getClass().getDeclaredFields();
        ArrayList<Field> fieldArrayList = new ArrayList<Field>();
        for(Field field : fields){
            if(field.getType()  == fieldType){
                field.setAccessible(true);
                fieldArrayList.add(field);
            }
        }

        return fieldArrayList;
    }

    public static ArrayList<Field> getArraysFields(Object instance, Class<?> fieldType) throws Exception {
        String[] values = fieldType.toString().split(" ");
        String fieldName = values[values.length - 1];
        Field[] fields = instance.getClass().getDeclaredFields();
        ArrayList<Field> fieldArrayList = new ArrayList<Field>();
        for(Field field : fields) {
            if (field.getType().isArray()) {
                if (field.getType().toString().contains(fieldName)) {
                    field.setAccessible(true);
                    fieldArrayList.add(field);
                }
            }
        }
        return fieldArrayList;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field, Object obj) {
        try {
            return (T) field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static void setFieldValue(Object instance, String field, Object value) {
        try {
            Field f = instance.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(instance, value);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public static void sendAllPacket(Object packet) throws Exception {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Object nmsPlayer = getNmsPlayer(p);
            Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection, packet);
        }
    }
    public static void sendListPacket(List<String> players, Object packet) {
        try {
            for (String name : players) {
                Object nmsPlayer = getNmsPlayer(Bukkit.getPlayer(name));
                Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
                connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection, packet);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public static void sendPlayerPacket(Player p, Object packet) throws Exception {
        Object nmsPlayer = getNmsPlayer(p);
        Object connection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
        connection.getClass().getMethod("sendPacket", Reflection.getClass("{nms}.Packet")).invoke(connection, packet);
    }

    public static void sendMessage(Player p, Object message) throws Exception {
        Object nmsPlayer = getNmsPlayer(p);
        nmsPlayer.getClass().getMethod("sendMessage", Reflection.getClass("{nms}.IChatBaseComponent")).invoke(nmsPlayer, message);

    }

    public static void sendMessages(Player p, Object message) throws Exception {
        Object nmsPlayer = getNmsPlayer(p);
        Class c = Reflection.getClass("{nms}.IChatBaseComponent");
        Method m = nmsPlayer.getClass().getMethod("sendMessage", new Class[]{ Array.newInstance(c, 4).getClass() });
        m.invoke(nmsPlayer, new Object[]{message});

    }

    public static int ping(Player p) throws Exception {
        Object nmsPlayer = Reflection.getNmsPlayer(p);
        return Integer.valueOf(getFieldValue(nmsPlayer, "ping").toString());
    }

    public static Field getFirstFieldByType(Class<?> clazz, Class<?> type) {
        for(Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == type) {
                return field;
            }
        }
        return null;
    }
}

