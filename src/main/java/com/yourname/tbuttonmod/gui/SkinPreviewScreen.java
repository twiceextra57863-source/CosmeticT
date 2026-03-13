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
        skinFiles.clear();
        File skinsFolder = new File(this.client.runDirectory, "TCosmetics/skins");
        
        if (skinsFolder.exists()) {
            File[] files = skinsFolder.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".png") || 
                name.toLowerCase().endsWith(".jpg"));
            
            if (files != null) {
                for (File file : files) {
                    skinFiles.add(file.getName());
                }
            }
        }
    }
    
    @Override
    protected void init() {
        int centerX = this.width / 2 - 60;
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("🔄 Reload"),
            button -> {
                loadSkins();
            }
        ).dimensions(centerX - 110, this.height - 40, 80, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("📁 Open Folder"),
            button -> {
                try {
                    File skinsFolder = new File(this.client.runDirectory, "TCosmetics/skins");
                    java.awt.Desktop.getDesktop().open(skinsFolder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        ).dimensions(centerX - 20, this.height - 40, 80, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("← Back"),
            button -> this.client.setScreen(parent)
        ).dimensions(centerX + 70, this.height - 40, 80, 20).build());
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
                Text.literal("No skins found!"), 
                this.width / 2, this.height / 2, 0xFF5555);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("Place .png files in TCosmetics/skins/"), 
                this.width / 2, this.height / 2 + 20, 0x888888);
        } else {
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal("Available Skins (" + skinFiles.size() + "):"), 
                50, 45, 0xFFFFFF);
            
            for (int i = 0; i < Math.min(skinFiles.size(), 15); i++) {
                int y = 70 + (i * 20) + scrollOffset;
                if (y > 45 && y < this.height - 50) {
                    context.drawTextWithShadow(this.textRenderer, 
                        Text.literal("• " + skinFiles.get(i)), 
                        70, y, 0xCCCCCC);
                }
            }
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollOffset += verticalAmount * 10;
        int maxScroll = Math.max(0, skinFiles.size() * 20 - (this.height - 150));
        scrollOffset = Math.max(-maxScroll, Math.min(0, scrollOffset));
        return true;
    }
}
