package im.hunnybon.mobsplosion;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Mobsplosion implements ModInitializer {
    public static MobsplosionConfig config = new MobsplosionConfig(true, true);
    public Gson daData = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
    Path configPath = Paths.get("config/mobsplosion.json");

    public void saveDaData() {
        try{
            if (configPath.toFile().exists()) {
                config = daData.fromJson(new String(Files.readAllBytes(configPath)), MobsplosionConfig.class);
            } else {
                Files.write(configPath, Collections.singleton(daData.toJson(config)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {
        saveDaData();
        System.out.println("Boom");
    }
}
