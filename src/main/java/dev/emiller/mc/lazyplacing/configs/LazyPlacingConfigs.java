package dev.emiller.mc.lazyplacing.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LazyPlacingConfigs {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File SERVER_CONFIG_FILE = new File("config/lazyplacing.json");

    @Nullable
    public static ServerConfig serverConfig;

    @Nullable
    public static ServerConfig hostConfig;

    public static void setHostConfig(@NotNull ServerConfig config) {
        hostConfig = config;
    }

    public static void clearHostConfig() {
        hostConfig = null;
    }

    public static void loadServerConfig() {
        serverConfig = loadFileConfig();
        hostConfig = serverConfig;
    }

    public static void clearServerConfig() {
        serverConfig = null;
        hostConfig = null;
    }

    public static ServerConfig loadFileConfig() {
        if (SERVER_CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(SERVER_CONFIG_FILE)) {
                return GSON.fromJson(reader, ServerConfig.class);
            } catch (IOException e) {
                System.out.println("LazyPlacing: Failed to load config server file! Default values will be used.");
                e.printStackTrace();
            }
        }

        ServerConfig config = new ServerConfig();

        try (FileWriter writer = new FileWriter(SERVER_CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            System.out.println("LazyPlacing: Failed to save server config file!");
            e.printStackTrace();
        }

        return config;
    }
}