package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class MenuScreen extends Screen {
    private final Screen parent;
    
    public MenuScreen(Screen parent) {
        super(Text.literal("Menu"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("📋 Main Dashboard"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX, 40, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("👤 Skin Preview"),
            button -> this.client.setScreen(new SkinPreviewScreen(this))
        ).dimensions(centerX, 70, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🎨 Theme Settings"),
            button -> this.client.setScreen(new ThemeSettingsScreen(this, null))
        ).dimensions(centerX, 100, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("📁 Open Skins Folder"),
            button -> {
                try {
                    java.io.File skinsFolder = new java.io.File(
                        this.client.runDirectory, "TCosmetics/skins");
                    java.awt.Desktop.getDesktop().open(skinsFolder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        ).dimensions(centerX, 130, 200, 20).build());
        
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
