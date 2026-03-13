package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MenuScreen extends Screen {
    private final Screen parent;
    
    public MenuScreen(Screen parent) {
        super(Text.literal("T-Cosmetics Menu"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2 - 100;
        int centerY = this.height / 2 - 60;
        
        // Skin Preview Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("👤 Skin Preview"),
            button -> client.setScreen(new SkinPreviewScreen(this))
        ).dimensions(centerX, centerY, 200, 20).build());
        
        // Settings Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("⚙ Settings"),
            button -> client.setScreen(new ThemeSettingsScreen(this, null, 0))
        ).dimensions(centerX, centerY + 30, 200, 20).build());
        
        // Back Button
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> client.setScreen(parent)
        ).dimensions(centerX, centerY + 80, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
