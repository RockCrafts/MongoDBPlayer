package xyz.rockcrafts.mc.MDBPlayer.events;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.rockcrafts.mc.MDBPlayer.MDBPlayer;
import xyz.rockcrafts.mc.MDBPlayer.data.PlayerData;

@RequiredArgsConstructor
public class PlayerLeave implements Listener {
    @NonNull MDBPlayer plugin;

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event){
        plugin.getMS().sendPlayerData(
                event.getPlayer());

    }

}
