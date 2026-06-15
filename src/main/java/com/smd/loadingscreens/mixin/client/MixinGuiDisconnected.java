package com.smd.loadingscreens.mixin.client;

import com.smd.loadingscreens.render.BackgroundRenderer;
import net.minecraft.client.gui.GuiDisconnected;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiDisconnected.class)
public abstract class MixinGuiDisconnected {

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiDisconnected;drawDefaultBackground()V"))
    private void loadingscreens$drawCustomDisconnectedBackground(GuiDisconnected screen) {
        if (!BackgroundRenderer.render(screen.mc, screen.width, screen.height)) {
            screen.drawDefaultBackground();
        }
    }
}
