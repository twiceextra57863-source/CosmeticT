package com.yourname.tbuttonmod;

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
            net.minecraft.client.MinecraftClient.getInstance().runDirectory, 
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
        super.init();
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(this.width / 2 - 50, this.height - 40, 100, 20).build());
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xDD1A1A2E);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lSKIN PREVIEW"), 
            this.width / 2, 20, 0xFF9B7EBD);
        
        // Skin list
        int y = 50;
        if (skinFiles.isEmpty()) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("No skins found in TCosmetics/skins/"), 
                this.width / 2, this.height / 2, 0xFF5555);
        } else {
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal("Available Skins:"), 
                50, 45, 0xFFFFFF);
            
            for (int i = 0; i < Math.min(skinFiles.size(), 10); i++) {
                context.drawTextWithShadow(this.textRenderer, 
                    Text.literal("• " + skinFiles.get(i)), 
                    70, y + (i * 20), 0xCCCCCC);
            }
            
            if (skinFiles.size() > 10) {
                context.drawTextWithShadow(this.textRenderer, 
                    Text.literal("... and " + (skinFiles.size() - 10) + " more"), 
                    70, y + 200, 0x888888);
            }
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
}
