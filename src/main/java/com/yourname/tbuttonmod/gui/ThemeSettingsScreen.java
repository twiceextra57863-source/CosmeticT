package com.yourname.tbuttonmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ThemeSettingsScreen extends Screen {
    private final Screen parent;
    
    public ThemeSettingsScreen(Screen parent) {
        super(Text.literal("Theme Settings"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2 - 100;
        
        String[] themes = {"🌙 Dark Purple", "🌊 Ocean Blue", "🌲 Forest Green", "⚫ Midnight Black"};
        
        for (int i = 0; i < themes.length; i++) {
            final int index = i;
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal(themes[i]),
                button -> {
                    // Apply theme logic here
                    this.client.setScreen(parent);
                }
            ).dimensions(centerX, 50 + (i * 30), 200, 20).build());
        }
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX, this.height - 40, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xDD1A1A2E);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lSELECT THEME"), 
            this.width / 2, 20, 0xFF9B7EBD);
        super.render(context, mouseX, mouseY, delta);
    }
}
