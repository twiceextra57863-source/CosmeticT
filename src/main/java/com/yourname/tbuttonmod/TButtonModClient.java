package com.yourname.tbuttonmod;

import com.yourname.tbuttonmod.gui.DashboardScreen; // Add this import
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TButtonModClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("TButtonClient");
    
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                addTButton((TitleScreen) screen);
            }
        });
    }
    
    private void addTButton(TitleScreen screen) {
        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        
        ButtonWidget tButton = ButtonWidget.builder(
            Text.literal("T"),
            button -> MinecraftClient.getInstance().setScreen(new DashboardScreen(screen))
        )
        .dimensions(width - 40, 10, 30, 30)
        .build();
        
        screen.addDrawableChild(tButton);
        LOGGER.info("T button added to TitleScreen");
    }
}
