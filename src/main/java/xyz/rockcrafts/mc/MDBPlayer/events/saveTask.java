package xyz.rockcrafts.mc.MDBPlayer.events;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.rockcrafts.mc.MDBPlayer.MDBPlayer;
import xyz.rockcrafts.mc.MDBPlayer.data.PlayerData;
import xyz.rockcrafts.mc.MDBPlayer.mongodb.MongoDBStorage;

@AllArgsConstructor
public class saveTask extends BukkitRunnable {
    @NonNull MongoDBStorage mongoDBStorage;
    @Override
    public void run() {
        PlayerData.sendAllPlayerData(mongoDBStorage);
    }
    public void start(MDBPlayer pl){
        this.runTaskTimer(pl,0L, pl.getCM().getConfig("config.yml").getInt("auto-save"));
    }
}
