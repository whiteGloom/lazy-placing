# Lazy Placing

[CurseForge](https://legacy.curseforge.com/minecraft/mc-mods/lazy-placing) •
[Modrinth](https://modrinth.com/mod/lazy-placing)

A Minecraft ~1.20.1 Fabric mod which changes one of the main mechanics in game - block placement timing.

In vanilla Minecraft, any block will be placed instantly, but now you'll have to wait a bit before block will be placed.

![Preview](additional/preview.gif)

## Info

By default, the time needed to place a block will be 0.6 seconds (see [configuration](#Configuration) section).

The mod...

* works in singleplayer, LAN and dedicated server;
* should be compatible with most of other mods, it should work with all block-items, which do not have custom block placement mechanics;
* affects only survival gamemode.

## Dependencies

This mod requires:
* [Minecraft Java Edition](https://www.minecraft.net/ru-ru/store/minecraft-deluxe-collection-pc) ~1.20.1;
* [Fabric Loader](https://fabricmc.net/use/) ^0.16.9.
* [Fabric API](https://github.com/FabricMC/fabric) ^0.92.2

(Version resolution from [Fabric Documentation](https://wiki.fabricmc.net/documentation:fabric_mod_json_spec#versionrange))

## Uninstallation

To uninstall the mod, just remove it from the `mods` folder.

## Configuration

When the game starts for the first time, the mod will generate a configuration file in `config/lazyplacing.json` (in root folder of Minecraft, not `mods` folder).

The file will have the following content:

```json
{
  "stablePlacingDuration": 12,
  "maxRandomAdditionDuration": 0
}
```

You can create this file by hand if you want to change the default without starting the game.

If the configuration file was deleted, mod will generate new one with default values.

### Properties:
* `stablePlacingDuration` - default value is `12` ticks (0.6 seconds). The minimum time needed to place a block.
* `maxRandomAdditionDuration` - default value is `0` ticks (0 seconds). The maximum clamp for random time, which may to the `stablePlacingDuration`.