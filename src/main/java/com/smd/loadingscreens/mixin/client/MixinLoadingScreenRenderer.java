package com.smd.loadingscreens.mixin.client;

import com.smd.loadingscreens.config.ModConfig;
import com.smd.loadingscreens.render.BackgroundRenderer;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LoadingScreenRenderer.class)
public abstract class MixinLoadingScreenRenderer {

    @Shadow @Final private Minecraft mc;

    @Redirect(
            method = "setLoadingProgress",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V", ordinal = 0),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V")
            )
    )
    private void loadingscreens$drawCustomProgressBackground(Tessellator tessellator) {
        if (!ModConfig.isEnabled()) {
            tessellator.draw();
            return;
        }

        tessellator.getBuffer().finishDrawing();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        BackgroundRenderer.render(this.mc, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
    }
}
