package dev.emiller.mc.lazyplacing.configs;

public class ServerConfig {
    public int stablePlacingDuration = 12;
    public int maxRandomAdditionDuration = 0;

    public ServerConfig() {}

    public ServerConfig(int stablePlacingDuration, int maxRandomAdditionDuration) {
        this.stablePlacingDuration = stablePlacingDuration;
        this.maxRandomAdditionDuration = maxRandomAdditionDuration;
    }
}
