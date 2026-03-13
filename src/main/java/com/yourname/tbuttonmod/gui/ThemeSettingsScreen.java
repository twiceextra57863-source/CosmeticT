package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemeSettingsScreen extends Screen {
    private final Screen parent;
    private final ThemeCallback callback;
    private int currentTheme;
    
    public interface ThemeCallback {
        void apply(int theme, int bgColor, int brColor, int accColor);
    }
    
    public ThemeSettingsScreen(Screen parent, ThemeCallback callback, int currentTheme) {
        super(Text.literal("Theme Settings"));
        this.parent = parent;
        this.callback = callback;
        this.currentTheme = currentTheme;
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Add theme buttons
        String[] themes = {"Dark Purple", "Ocean Blue", "Forest Green", "Midnight Black"};
        int[] bgColors = {0xDD1A1A2E, 0xDD1A2E3A, 0xDD1A2E1A, 0xDD0A0A0A};
        int[] brColors = {0xFF6B4E71, 0xFF4E7B91, 0xFF4E915E, 0xFF4A4A4A};
        int[] accColors = {0xFF9B7EBD, 0xFF7EB2D9, 0xFF7ED99B, 0xFF8A8A8A};
        
        for (int i = 0; i < themes.length; i++) {
            final int index = i;
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(themes[i]),
                button -> {
                    callback.apply(index, bgColors[index], brColors[index], accColors[index]);
                    client.setScreen(parent);
                }
            ).dimensions(this.width / 2 - 100, 50 + i * 30, 200, 20).build());
        }
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("Cancel"),
            button -> client.setScreen(parent)
        ).dimensions(this.width / 2 - 100, this.height - 40, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
