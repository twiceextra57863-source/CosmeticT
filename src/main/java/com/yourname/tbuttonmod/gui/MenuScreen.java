package com.yourname.tbuttonmod;

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
        super.init();
        
        int centerX = this.width / 2 - 100;
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("📋 Main Dashboard"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX, 50, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("👤 Skin Preview"),
            button -> this.client.setScreen(new SkinPreviewScreen(this))
        ).dimensions(centerX, 80, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🎨 Theme Settings"),
            button -> this.client.setScreen(new ThemeSettingsScreen(this))
        ).dimensions(centerX, 110, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("📁 Open Skins Folder"),
            button -> {
                try {
                    java.awt.Desktop.getDesktop().open(new java.io.File(
                        net.minecraft.client.MinecraftClient.getInstance().runDirectory, 
                        "TCosmetics/skins"
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        ).dimensions(centerX, 140, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX, this.height - 40, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xDD1A1A2E);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lT-COSMETICS MENU"), 
            this.width / 2, 20, 0xFF9B7EBD);
        super.render(context, mouseX, mouseY, delta);
    }
}
