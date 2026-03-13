package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemeSettingsScreen extends Screen {
    private final Screen parent;
    private final ThemeCallback callback;
    
    public interface ThemeCallback {
        void apply(int themeIndex, int bgColor, int brColor, int accColor);
    }
    
    public ThemeSettingsScreen(Screen parent, ThemeCallback callback) {
        super(Text.literal("Theme Settings"));
        this.parent = parent;
        this.callback = callback;
    }
    
    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        
        // Theme 1: Dark Purple
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🌙 Dark Purple"),
            button -> {
                if (callback != null) 
                    callback.apply(0, 0xDD1A1A2E, 0xFF6B4E71, 0xFF9B7EBD);
                this.client.setScreen(parent);
            }
        ).dimensions(centerX, 40, 200, 20).build());
        
        // Theme 2: Ocean Blue
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🌊 Ocean Blue"),
            button -> {
                if (callback != null) 
                    callback.apply(1, 0xDD1A2E3A, 0xFF4E7B91, 0xFF7EB2D9);
                this.client.setScreen(parent);
            }
        ).dimensions(centerX, 70, 200, 20).build());
        
        // Theme 3: Forest Green
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🌲 Forest Green"),
            button -> {
                if (callback != null) 
                    callback.apply(2, 0xDD1A2E1A, 0xFF4E915E, 0xFF7ED99B);
                this.client.setScreen(parent);
            }
        ).dimensions(centerX, 100, 200, 20).build());
        
        // Theme 4: Midnight Black
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("⚫ Midnight Black"),
            button -> {
                if (callback != null) 
                    callback.apply(3, 0xDD0A0A0A, 0xFF4A4A4A, 0xFF8A8A8A);
                this.client.setScreen(parent);
            }
        ).dimensions(centerX, 130, 200, 20).build());
        
        // Back button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX, this.height - 40, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
