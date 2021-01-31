package xyz.rockcrafts.mc.MDBPlayer.data;

import lombok.NonNull;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class SerializationManager {

    public static @NonNull String bSE(Object object){
        return encode(bukkitSerialize(object));
    }
    public static @NonNull Object bDD(String toDecode){
        return bukkitDeserialize(decode(toDecode));
    }
    public static byte[] bukkitSerialize(Object object) {
        try {
        ByteArrayOutputStream io = new ByteArrayOutputStream();
        BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
        //Write to ObjectOutput, flush to Byte array output
        os.writeObject(object);
        os.flush();
        return io.toByteArray();

    } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Object bukkitDeserialize(byte[] bytes) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            //Write to ObjectOutput, flush to Byte array output
            return is.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static byte[] decode(String encoded){
        return Base64.getDecoder().decode(encoded);
    }

}
