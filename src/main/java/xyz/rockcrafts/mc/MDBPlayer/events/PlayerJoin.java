package xyz.rockcrafts.mc.MDBPlayer.events;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.rockcrafts.mc.MDBPlayer.MDBPlayer;
import xyz.rockcrafts.mc.MDBPlayer.data.PlayerData;

import java.util.logging.Level;

@RequiredArgsConstructor
public class PlayerJoin implements Listener {

    @NonNull MDBPlayer plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PlayerData pd = plugin.getMS().getPlayerData(event.getPlayer());
        if(plugin.getMS().contains(event.getPlayer().getUniqueId().toString()))

            PlayerData.applyAll(event.getPlayer(), pd);
        else {
            plugin.getMS().sendPlayerData(event.getPlayer());
            plugin.getLogger().log(Level.INFO,"Sent Data");
        }
    }
}
