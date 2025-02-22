/*
This is a modified version of the abandoned addon skLib. It adds useful Redis syntax to Skript.
 */

package org.bcdtech;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public class Main extends JavaPlugin {



    String pluginName;
    static Main instance;
    static SkriptAddon addonInstance;
    static RedisClient client;
    static StatefulRedisConnection<String, String> publisher;
    static StatefulRedisPubSubConnection<String, String> connection;
    static RedisPubSubAsyncCommands<String, String> commands;
    static StatefulRedisPubSubConnection<String, String> subscriber;
    static RedisPubSubListener<String, String> listener;
    static Boolean redisFailure = false;


    @Override
    public void onEnable() {
        this.pluginName = getPluginInstance().getName();
        instance = this;
        //Register the addon with skript
        addonInstance = Skript.registerAddon(instance);
        //End
        loadPluginConfig();
        getServer().getPluginManager().registerEvents(new UtilPlayerInfo(), getPluginInstance());
        Util.Log(Component.text("Loaded blue cheese development plugin " + pluginName));
        Util.Log(Component.text("Attempting to connect to Redis..."));
        //Try connecting to Redis before loading the addon syntax
        RedisURI uri = RedisURI.Builder
                .redis(getPluginInstance().getConfig().getString("ip"), getPluginInstance().getConfig().getInt("port"))
                .withAuthentication("default", getPluginInstance().getConfig().getString("password"))
                .build();
        try {
            client = RedisClient.create(uri); //Create our client
            connection = client.connectPubSub(); //Connect client to server
            publisher = client.connect(); //Publisher client
        } catch (RedisConnectionException e) {
            redisFailure = true;
            throw new RuntimeException(e);
        }
        if (connectedToRedis()){
            commands = connection.async(); //Object to call methods on
            subscriber = connection; //Connect to channels
            Util.Log(Component.text("Successfully connected to redis!"));
            List<String> channels = getPluginInstance().getConfig().getStringList("redischannels");
            channels
                    .forEach(Main::registerChannel);
            //Load skript syntax
            if (Skript.isAcceptRegistrations()) {
                try {
                    addonInstance.loadClasses("org.bcdtech.skriptredis");
                    registerListener();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            Util.Error(Component.text("Failed to establish connection!"));
        }
    }



    //Terminate all Redis connections on plugin disable
    @Override
    public void onDisable(){
        if (connectedToRedis()){
            getRedisSubscriber().removeListener(listener);
            connection.close(); //Severe client's connection
            publisher.close();
            client.shutdown(); //Terminate client
        }
    }

    public void registerListener(){
        listener = new RedisPubSubListener<>() {

            @Override
            public void message(String channel, String message) {
                BukkitRedisMsg event = new BukkitRedisMsg(message, channel);
                event.callEvent();
            }

            @Override
            public void message(String pattern, String channel, String message) {
            }

            @Override
            public void subscribed(String channel, long count) {
            }

            @Override
            public void psubscribed(String pattern, long count) {

            }

            @Override
            public void unsubscribed(String channel, long count) {

            }

            @Override
            public void punsubscribed(String pattern, long count) {

            }
        };
        Util.Log(Component.text("Registering listener"));
        getRedisSubscriber().addListener(listener);
    }

    public void loadPluginConfig(){
        saveDefaultConfig();
    }

    public static void registerChannel(String channel){
        getRedisSubscriber().async().subscribe(channel);
        Util.Log(Component.text("Registering channel " + channel));
    }

    public static void sendRedisMsg(String channel, String msg){
        RedisFuture<Long> test = getRedisPublisher().async().publish(channel, msg);
        //System.out.println("Clients received: " + test);
    }

    public static Plugin getPluginInstance(){
        return getPlugin(Main.class);
    }
    @SuppressWarnings("unused")
    public static SkriptAddon getAddonInstance(){
        return addonInstance;
    }
    @SuppressWarnings("unused")
    public static RedisClient getRedisClient(){
        return client;
    }
    @SuppressWarnings("unused")
    public static StatefulRedisPubSubConnection<String, String> getRedisConnection(){
        return connection;
    }

    public static RedisPubSubAsyncCommands<String, String> getRedisCommandExecutor(){
        return commands;
    }

    public static StatefulRedisPubSubConnection<String, String> getRedisSubscriber(){
        return subscriber;
    }
    
    public static StatefulRedisConnection<String, String> getRedisPublisher(){
        return publisher;
    }

    public static boolean connectedToRedis(){
        if (redisFailure){
            return false;
        } else {
            return true;
        }
    }

}