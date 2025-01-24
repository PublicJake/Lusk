package it.jakegblp.lusk.utils;

import com.vdurmont.semver4j.Semver;
import it.jakegblp.lusk.api.MinecraftAdapter;
import it.jakegblp.lusk.api.SkriptAdapter;
import it.jakegblp.lusk.version.minecraft.v1_20.Minecraft_1_20;
import it.jakegblp.lusk.version.minecraft.v1_21.Minecraft_1_21;
import it.jakegblp.lusk.version.skript.v2_10_0.Skript_2_10_0;
import it.jakegblp.lusk.version.skript.v2_6_4.Skript_2_6_4;
import it.jakegblp.lusk.version.skript.v2_7_3.Skript_2_7_3;
import it.jakegblp.lusk.version.skript.v2_8_7.Skript_2_8_7;
import it.jakegblp.lusk.version.skript.v2_9_5.Skript_2_9_5;

import static it.jakegblp.lusk.utils.Constants.*;

public class VersionResolver {

    public static SkriptAdapter getSkriptAdapter(Semver version) {
        int major = version.getMajor(),
            minor = version.getMinor(),
            patch = version.getPatch();
        SkriptAdapter skriptAdapter = switch (major) {
            case 2 -> switch (minor) {
                case 6 -> new Skript_2_6_4();
                case 7 -> new Skript_2_7_3();
                case 8 -> new Skript_2_8_7();
                case 9 -> new Skript_2_9_5();
                case 10 -> new Skript_2_10_0();
                default -> null;
            };
            default -> null;
        };
        if (skriptAdapter == null) {
            skriptAdapter = getSkriptAdapter(VERSION_SKRIPT_NEWEST_SUPPORTED);
        }
        return skriptAdapter;
    }

    public static MinecraftAdapter getMinecraftAdapter(Semver version) {
        int major = version.getMajor(),
                minor = version.getMinor(),
                patch = version.getPatch();
        return switch (major) {
            case 1 -> switch (minor) {
                case 20 -> new Minecraft_1_20();
                case 21 -> new Minecraft_1_21();
                default -> new Minecraft_1_21();
            };
            default -> new Minecraft_1_21();
        };
    }

}