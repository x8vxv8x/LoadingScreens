package com.smd.loadingscreens.mixin.client;

import com.smd.loadingscreens.render.BackgroundRenderer;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiConnecting.class)
public abstract class MixinGuiConnecting {

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/GuiConnecting;drawDefaultBackground()V"))
    private void loadingscreens$drawCustomConnectingBackground(GuiConnecting screen) {
        if (!BackgroundRenderer.render(screen.mc, screen.width, screen.height)) {
            screen.drawDefaultBackground();
        }
    }
}
