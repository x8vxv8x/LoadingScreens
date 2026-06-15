package com.smd.loadingscreens.config;

import com.smd.loadingscreens.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

public final class ModConfig {

    public static final String DEFAULT_BACKGROUND_TEXTURE = "minecraft:textures/gui/title/background/panorama_0.png";
    public static final ResourceLocation DEFAULT_BACKGROUND_RESOURCE = new ResourceLocation(DEFAULT_BACKGROUND_TEXTURE);

    private static Configuration configuration;
    private static boolean enabled = true;
    private static ResourceLocation backgroundTexture = DEFAULT_BACKGROUND_RESOURCE;

    private ModConfig() {
    }

    public static void init(File configDir) {
        configuration = new Configuration(new File(configDir, Tags.MOD_ID + ".cfg"));
        load();
    }

    public static void load() {
        if (configuration == null) {
            return;
        }

        enabled = configuration.getBoolean(
                "enabled",
                Configuration.CATEGORY_GENERAL,
                true,
                "Whether Loading Screens replaces vanilla loading backgrounds."
        );

        Property backgroundTextureProperty = configuration.get(
                Configuration.CATEGORY_GENERAL,
                "backgroundTexture",
                DEFAULT_BACKGROUND_TEXTURE,
                "Resource path for the loading background texture. Invalid values fall back to the default."
        );
        String backgroundTextureValue = backgroundTextureProperty.getString().trim();

        ResourceLocation parsedBackgroundTexture = parseResourceLocation(backgroundTextureValue);
        if (parsedBackgroundTexture == null) {
            backgroundTexture = DEFAULT_BACKGROUND_RESOURCE;
            backgroundTextureProperty.set(DEFAULT_BACKGROUND_TEXTURE);
        } else {
            backgroundTexture = parsedBackgroundTexture;
            if (!backgroundTextureValue.equals(backgroundTextureProperty.getString())) {
                backgroundTextureProperty.set(backgroundTextureValue);
            }
        }

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static ResourceLocation getBackgroundTexture() {
        return backgroundTexture;
    }

    private static ResourceLocation parseResourceLocation(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new ResourceLocation(value.trim());
        } catch (Exception ignored) {
            return null;
        }
    }
}
