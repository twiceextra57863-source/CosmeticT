package com.yourname.tbuttonmod;

import com.yourname.tbuttonmod.gui.DashboardScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TButtonModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                // Create button with proper positioning
                int buttonX = screen.width - 40;
                int buttonY = 10;
                
                ButtonWidget tButton = ButtonWidget.builder(
                    Text.literal("T"),
                    button -> MinecraftClient.getInstance().setScreen(new DashboardScreen((TitleScreen) screen))
                )
                .dimensions(buttonX, buttonY, 30, 30)
                .build();
                
                // Add to screen's children
                screen.addDrawableChild(tButton);
            }
        });
    }
}
