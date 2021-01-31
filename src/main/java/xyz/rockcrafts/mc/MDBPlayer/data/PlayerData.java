package xyz.rockcrafts.mc.MDBPlayer.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.rockcrafts.mc.MDBPlayer.mongodb.MongoDBStorage;

@Data
public class PlayerData {
    private String uuid;
    private String items;
    private String ec;
    private String location;
    private String world;

    public PlayerData(String uuid, String items, String world, String ec, String loc){
        this.uuid = uuid;
        this.items = items;
        this.location = loc;
        this.world = world;
        this.ec = ec;
    }
    public PlayerData(Player player){
        this.uuid = player.getUniqueId().toString();
        this.items = SerializationManager.bSE(player.getInventory().getContents());
        this.world = player.getLocation().getWorld().getName();
        this.ec = SerializationManager.bSE((player.getEnderChest().getContents()));
        this.location = SerializationManager.bSE(player.getLocation());
    }
    /*
    *
    *   Move Stored data to an online Player.
    *
    *   @param player the player to apply to
    *
    *
     */
    public static void applyAll(Player player, PlayerData pd){
        //Loads the World/Creates it if not found.
        WorldCreator.name(pd.getWorld()).createWorld();
        if(pd.getLocation()!=null)
            player.teleport((Location) SerializationManager.bDD(pd.getLocation()));
        applyItems(player, pd);
    }
    public static void applyItems(Player player, PlayerData pd){
        if(pd.getItems()!=null)
            player.getInventory().setContents((
                    ItemStack[]) SerializationManager.bDD(pd.getItems()));
        if(pd.getEc()!=null)
            player.getEnderChest().setContents((
                    ItemStack[]) SerializationManager.bDD(pd.getEc()));
    }
    public static void sendAllPlayerData(MongoDBStorage mS){
        for(Player p: Bukkit.getOnlinePlayers()){
            mS.sendPlayerData(p);
        }
    }

}
