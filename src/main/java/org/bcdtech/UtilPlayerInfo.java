package org.bcdtech;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bcdtech.Main.*;

public class UtilPlayerInfo implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!connectedToRedis()){
            if(p.hasPermission("Rank.JrAdmin")){
                BukkitRunnable wait = new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.sendMessage(Component.text("<red>The server failed to connect to a Redis Database! Check the configuration file."));
                    }
                };
                wait.runTaskLater(getPluginInstance(), 40);
            }
        }
    }
}
