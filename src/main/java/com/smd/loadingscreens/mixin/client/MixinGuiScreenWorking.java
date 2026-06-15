package com.smd.loadingscreens.mixin.client;

import com.smd.loadingscreens.render.BackgroundRenderer;
import net.minecraft.client.gui.GuiScreenWorking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiScreenWorking.class)
public abstract class MixinGuiScreenWorking {

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreenWorking;drawDefaultBackground()V"))
    private void loadingscreens$drawCustomWorkingBackground(GuiScreenWorking screen) {
        if (!BackgroundRenderer.render(screen.mc, screen.width, screen.height)) {
            screen.drawDefaultBackground();
        }
    }
}
