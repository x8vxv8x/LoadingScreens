package com.smd.loadingscreens.mixin.client;

import com.smd.loadingscreens.render.BackgroundRenderer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiDownloadTerrain.class)
public abstract class MixinGuiDownloadTerrain {

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiDownloadTerrain;drawBackground(I)V"))
    private void loadingscreens$drawCustomDownloadTerrainBackground(GuiDownloadTerrain screen, int tint) {
        if (!BackgroundRenderer.render(screen.mc, screen.width, screen.height)) {
            screen.drawBackground(tint);
        }
    }
}
