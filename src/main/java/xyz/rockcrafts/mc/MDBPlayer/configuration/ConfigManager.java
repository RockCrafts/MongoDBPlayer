package xyz.rockcrafts.mc.MDBPlayer.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.rockcrafts.mc.MDBPlayer.MDBPlayer;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import static com.google.common.io.Resources.getResource;

public class ConfigManager {
    private MDBPlayer plugin;

    public ConfigManager(MDBPlayer plugin) {
        this.plugin = plugin;
    }


    private YamlConfiguration getYamlConfig(String name){
        File file = new File(this.plugin.getDataFolder(), name);

        if(!file.exists()) {
            try {
                file.createNewFile();
                return getDefaultValues(file);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
            return YamlConfiguration.loadConfiguration(file);

    }
    public FileConfiguration getConfig(String name) {
        return getYamlConfig((name));
    }
    public YamlConfiguration getDefaultValues(File file){
        YamlConfiguration newYaml = new YamlConfiguration();
        Reader defConfigStream = null;
        // Get File from jar
        try {
            defConfigStream = new InputStreamReader(this.plugin.getResource(file.getName()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            newYaml.setDefaults(defConfig);
            //Copy default values
            newYaml.options().copyDefaults(true);
            try {
                newYaml.save(file);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            }

        return newYaml;
    }
    public void makePluginFolder(){
        if(!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdir();
    }


}
