package xyz.rockcrafts.mc.MDBPlayer.events;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.plugin.PluginManager;
import xyz.rockcrafts.mc.MDBPlayer.MDBPlayer;

import java.beans.EventHandler;

public class EventManager {
    MDBPlayer pl;
    PluginManager pm;
    public EventManager(MDBPlayer pl){
        this.pl = pl;
        this.pm = pl.getPM();
        registerEvents();
    }
    private void registerEvents(){
        pm.registerEvents(new PlayerJoin(this.pl), this.pl);
        pm.registerEvents(new PlayerLeave(this.pl), this.pl);
    }
}
