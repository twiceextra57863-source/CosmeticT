package com.yourname.tbuttonmod.mixin;

import com.yourname.tbuttonmod.gui.DashboardScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    
    @Inject(method = "init", at = @At("TAIL"))
    private void addTButton(CallbackInfo ci) {
        TitleScreen screen = (TitleScreen)(Object)this;
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        
        // Simple button without custom rendering
        ButtonWidget tButton = ButtonWidget.builder(
            Text.literal("T"),
            button -> {
                client.setScreen(new DashboardScreen(screen));
            }
        )
        .dimensions(width - 40, 10, 30, 30)
        .build();
        
        screen.addDrawableChild(tButton);
    }
}
