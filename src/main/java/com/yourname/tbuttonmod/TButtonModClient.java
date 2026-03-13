package com.yourname.tbuttonmod;

import com.yourname.tbuttonmod.gui.DashboardScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TButtonModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof TitleScreen) {
                ButtonWidget tButton = ButtonWidget.builder(
                    Text.literal("T"),
                    button -> client.setScreen(new DashboardScreen((TitleScreen) screen))
                )
                .dimensions(screen.width - 40, 10, 30, 30)
                .build();
                
                Screens.getButtons(screen).add(tButton);
            }
        });
    }
}
