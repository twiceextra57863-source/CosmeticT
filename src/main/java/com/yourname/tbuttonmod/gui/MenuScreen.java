package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

public class MenuScreen extends Screen {
    private final Screen parent;
    
    public MenuScreen(Screen parent) {
        super(Text.literal("Menu"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Add menu buttons
        this.addDrawableChild(new net.minecraft.client.gui.widget.ButtonWidget.Builder(
            Text.literal("Skin Preview"),
            button -> MinecraftClient.getInstance().setScreen(new SkinPreviewScreen(this))
        ).dimensions(this.width / 2 - 100, this.height / 2 - 40, 200, 20).build());
        
        this.addDrawableChild(new net.minecraft.client.gui.widget.ButtonWidget.Builder(
            Text.literal("Back"),
            button -> MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(this.width / 2 - 100, this.height / 2 + 20, 200, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
