package org.bcdtech;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BukkitRedisMsg extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private String MESSAGE;
    private String CHANNEL;

    public BukkitRedisMsg(String msg, String chn){
        super(true);
        this.MESSAGE = msg;
        this.CHANNEL = chn;
    }

    public void setMessage(String msg){
        this.MESSAGE = msg;
    }

    public void setChannel(String chn){
        this.CHANNEL = chn;
    }

    public String getChannel(){
        return this.CHANNEL;
    }

    public String getMessage(){
        return this.MESSAGE;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
