package org.bcdtech;

import net.kyori.adventure.text.Component;



public class Util extends Main {

    public static void Log(Component msg){
        getPluginInstance().getComponentLogger().info(msg);
    }

    public static void Error(Component msg){
        getPluginInstance().getComponentLogger().error(msg);
    }

    public static String fixLiteralString(String string){
        return string.replace("\"", "");
    }
}
