package com.yourname.tbuttonmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TButtonModClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TButtonMod.MOD_ID + "-client");
    
    @Override
    public void onInitializeClient() {
        LOGGER.info("TButton Mod Client initializing...");
        
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                LOGGER.info("TitleScreen detected, adding T button...");
                addTButtonToTitleScreen(screen);
            }
        });
        
        LOGGER.info("TButton Mod Client initialized!");
    }
    
    private void addTButtonToTitleScreen(Screen screen) {
        try {
            // Create T button
            ButtonWidget tButton = ButtonWidget.builder(
                Text.literal("T"),
                button -> {
                    LOGGER.info("T button clicked! Opening dashboard...");
                    MinecraftClient.getInstance().setScreen(new DashboardScreen(screen));
                }
            )
            .dimensions(screen.width - 40, 10, 30, 30) // Position: top-right corner
            .build();
            
            // Add button to screen
            Screens.getButtons(screen).add(tButton);
            
            LOGGER.info("T button added successfully!");
        } catch (Exception e) {
            LOGGER.error("Failed to add T button", e);
        }
    }
}
