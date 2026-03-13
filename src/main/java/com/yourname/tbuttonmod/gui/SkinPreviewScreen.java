package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkinPreviewScreen extends Screen {
    private final Screen parent;
    private List<String> skinFiles = new ArrayList<>();
    private int scrollOffset = 0;
    
    public SkinPreviewScreen(Screen parent) {
        super(Text.literal("Skin Preview"));
        this.parent = parent;
        loadSkins();
    }
    
    private void loadSkins() {
        File skinsFolder = new File(
            this.client.runDirectory, 
            "TCosmetics/skins"
        );
        
        if (skinsFolder.exists()) {
            File[] files = skinsFolder.listFiles((dir, name) -> 
                name.endsWith(".png") || name.endsWith(".jpg"));
            
            if (files != null) {
                for (File file : files) {
                    skinFiles.add(file.getName());
                }
            }
        }
    }
    
    @Override
    protected void init() {
        int centerX = this.width / 2 - 50;
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🔄 Reload"),
            button -> {
                skinFiles.clear();
                loadSkins();
            }
        ).dimensions(centerX - 100, this.height - 40, 80, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX + 20, this.height - 40, 80, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lSKIN PREVIEW"), 
            this.width / 2, 20, 0xFF9B7EBD);
        
        // Skin list
        if (skinFiles.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("No skins found in TCosmetics/skins/"), 
                this.width / 2, this.height / 2, 0xFF5555);
            
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("Add .png files to the folder and restart"), 
                this.width / 2, this.height / 2 + 20, 0x888888);
        } else {
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal("Available Skins (" + skinFiles.size() + "):"), 
                50, 45, 0xFFFFFF);
            
            for (int i = 0; i < Math.min(skinFiles.size(), 15); i++) {
                int y = 70 + (i * 20);
                context.drawTextWithShadow(this.textRenderer, 
                    Text.literal("• " + skinFiles.get(i)), 
                    70, y, 0xCCCCCC);
            }
            
            if (skinFiles.size() > 15) {
                context.drawTextWithShadow(this.textRenderer, 
                    Text.literal("... and " + (skinFiles.size() - 15) + " more"), 
                    70, 70 + (15 * 20), 0x888888);
            }
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
}
