package com.smd.loadingscreens.render;

import com.smd.loadingscreens.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public final class BackgroundRenderer {

    private static ResourceLocation cachedConfiguredTexture;
    private static ResourceLocation cachedResolvedTexture;

    private BackgroundRenderer() {
    }

    public static boolean render(Minecraft minecraft, int width, int height) {
        if (!ModConfig.isEnabled() || minecraft == null || width <= 0 || height <= 0) {
            return false;
        }

        renderTexture(minecraft, width, height, resolveTexture(minecraft, ModConfig.getBackgroundTexture()));
        return true;
    }

    private static ResourceLocation resolveTexture(Minecraft minecraft, ResourceLocation configuredTexture) {
        if (configuredTexture.equals(cachedConfiguredTexture) && cachedResolvedTexture != null) {
            return cachedResolvedTexture;
        }

        cachedConfiguredTexture = configuredTexture;
        if (resourceExists(minecraft, configuredTexture)) {
            cachedResolvedTexture = configuredTexture;
            return cachedResolvedTexture;
        }
        cachedResolvedTexture = ModConfig.DEFAULT_BACKGROUND_RESOURCE;
        return cachedResolvedTexture;
    }

    private static boolean resourceExists(Minecraft minecraft, ResourceLocation texture) {
        try (IResource ignored = minecraft.getResourceManager().getResource(texture)) {
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private static void renderTexture(Minecraft minecraft, int width, int height, ResourceLocation texture) {
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(texture);
        drawTexturedQuad(width, height);
    }

    private static void drawTexturedQuad(int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, height, 0.0D).tex(0.0D, 1.0D).endVertex();
        buffer.pos(width, height, 0.0D).tex(1.0D, 1.0D).endVertex();
        buffer.pos(width, 0, 0.0D).tex(1.0D, 0.0D).endVertex();
        buffer.pos(0, 0, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
    }
}
