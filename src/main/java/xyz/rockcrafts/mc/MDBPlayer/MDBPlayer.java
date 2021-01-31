package xyz.rockcrafts.mc.MDBPlayer;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import lombok.Getter;
import lombok.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.rockcrafts.mc.MDBPlayer.configuration.ConfigManager;
import xyz.rockcrafts.mc.MDBPlayer.data.PlayerData;
import xyz.rockcrafts.mc.MDBPlayer.events.EventManager;
import xyz.rockcrafts.mc.MDBPlayer.events.saveTask;
import xyz.rockcrafts.mc.MDBPlayer.mongodb.MongoDBStorage;

import javax.print.Doc;
import java.beans.EventHandler;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

public class MDBPlayer extends JavaPlugin {
    @Getter private MongoDBStorage mS;
    @Getter private ConfigManager cM;
    @Getter private EventManager eM;
    @Getter private PluginManager pM;

    @Override
    public void onEnable() {
        disableMongoLogger();
        initalizeManagers();
        if(mS.isConencted()) {
            setupEM();
            startTasks();
        }
    }

    @Override
    public void onDisable() {
        PlayerData.sendAllPlayerData(getMS());
    }
    /*
    *
    * Methods for plugin setup
    *
     */
    private void initalizeManagers(){
        cM = new ConfigManager(this);
        mS = new MongoDBStorage();
        pM = Bukkit.getPluginManager();
        cM.makePluginFolder();
        setupConfig();
    }

    private void setupConfig(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Trying to connect to Database..");
        cM.getConfig("config.yml");
        if(mS.connectViaConfig(cM.getConfig("mongodb.yml"))){
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB via Config");
                return;
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "!!Error!! when to MongoDB via Config");
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "!!Error!! SHUTTING DOWN.");
        getPM().disablePlugin(this);
    }
    private void setupEM(){
        eM = new EventManager(this);
    }
    private void startTasks(){

        new saveTask(this.getMS()).start(this);
    }
    private void disableMongoLogger(){
        Logger.getLogger( "org.mongodb.driver" ).setLevel(Level.OFF);
    }





}
