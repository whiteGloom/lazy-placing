package dev.emiller.mc.lazyplacing.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LazyPlacingConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/lazyplacing.json");

    public int stablePlacingDuration = 12;

    public int maxRandomAdditionDuration = 0;

    public static LazyPlacingConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, LazyPlacingConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new LazyPlacingConfig().save();
    }

    public LazyPlacingConfig save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }
}