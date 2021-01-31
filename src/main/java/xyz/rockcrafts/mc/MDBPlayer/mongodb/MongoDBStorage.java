package xyz.rockcrafts.mc.MDBPlayer.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Data;
import lombok.*;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import xyz.rockcrafts.mc.MDBPlayer.data.PlayerData;

@Data
public class MongoDBStorage {
    private @NonNull MongoClient mongoClient;
    private @NonNull MongoDatabase database;
    private @NonNull MongoCollection<Document> playerCollection;
    private boolean conencted;
    public MongoDBStorage(){
    }
    public boolean connect(String uri, String db, String collection){
        try {
            this.conencted = false;
            if(uri.equals("mongodb://{user1}:{pwd1}@{host1}/{db}")){
                return false;
            }
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(db);
            database.runCommand(new Document("ping", 1));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        this.conencted = true;
        playerCollection = database.getCollection(collection);
        return true;
    }
    public Document getDocument(String uuid){
        return getPlayerCollection().find(new Document("uuid",uuid)).first();
    }
    public boolean connectViaConfig(FileConfiguration config){
        return connect(config.getString("uri"),
                config.getString("db"),
                config.getString("collection"));
    }

    private Bson getPDBson(String uuid, PlayerData pd){
        return new Document("uuid", uuid).append(
                "world", pd.getWorld()).append(
                "enderchest",pd.getEc()).append(
                "items", pd.getItems()).append(
                "location", pd.getLocation()
        );
    }
    /*
     * Sends Player data to a UUID in DB.
     *
     */
    @Deprecated
    public void sendPlayerData(String uuid, PlayerData pd){
        Document found = (Document) getDocument(uuid);
        Bson updatedValue = getPDBson(uuid, pd);
        Bson updateOperation = new Document("$set", updatedValue);
            if(found != null)
                this.getPlayerCollection().updateOne(found, updateOperation);
            else
                this.getPlayerCollection().insertOne((Document) updatedValue);

    }
    /*
     * Sends Player's data, to their uuid in DB.
     *
     */
    public void sendPlayerData(Player player){
        PlayerData pd = new PlayerData(player);
        Document found = (Document) getDocument(player.getUniqueId().toString());
        Bson updatedValue = getPDBson(player.getUniqueId().toString(), pd);
        Bson updateOperation = new Document("$set", updatedValue);
        if(found != null) {
            this.getPlayerCollection().updateOne(found, updateOperation);
        }
        else
            this.getPlayerCollection().insertOne((Document) updatedValue);

    }
    public PlayerData getPlayerData(Player player){
        Document found = (Document) getDocument(player.getUniqueId().toString());
        if(found != null) {
            return new PlayerData(
                    found.getString("uuid"),
                    found.getString("items"),
                    found.getString("world"),
                    found.getString("enderchest"),
                    found.getString("location")
            );
        }
        else{
            return new PlayerData(player);
        }
    }
    public boolean contains(String uuid){
        return getPlayerCollection().find(Filters.eq("uuid", uuid))
                .first() != null;
    }
    public boolean contains(PlayerData pd){
        return getPlayerCollection().find(Filters.eq("uuid", pd.getUuid()))
                .first() != null;
    }

}

