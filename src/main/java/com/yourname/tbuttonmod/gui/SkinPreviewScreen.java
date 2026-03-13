package com.yourname.tbuttonmod.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import com.yourname.tbuttonmod.skin.SkinManager;

import java.util.List;
import java.util.UUID;

public class SkinPreviewScreen extends Screen {
    private final Screen parent;
    private PlayerEntity previewPlayer;
    private float mouseX, mouseY;
    private SkinManager.SkinEntry selectedSkin;
    private List<SkinManager.SkinEntry> skins;
    private int scrollOffset = 0;
    private boolean clicked = false;
    
    // Colors
    private final int backgroundColor = 0xDD1A1A2E;
    private final int borderColor = 0xFF6B4E71;
    private final int accentColor = 0xFF9B7EBD;
    
    protected SkinPreviewScreen(Screen parent) {
        super(Text.literal("Skin Preview"));
        this.parent = parent;
        this.skins = SkinManager.getSkins();
        
        // FIXED: Correct PlayerEntity constructor for Minecraft 1.21
        createPreviewPlayer();
    }
    
    private void createPreviewPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        
        if (world != null) {
            try {
                // CORRECT CONSTRUCTOR: PlayerEntity(World world, BlockPos pos, float yaw, GameProfile profile)
                GameProfile profile = new GameProfile(UUID.randomUUID(), "Preview");
                BlockPos spawnPos = new BlockPos(0, 64, 0); // Default spawn position
                float yaw = 0.0f; // Default rotation
                
                this.previewPlayer = new PlayerEntity(world, spawnPos, yaw, profile) {
                    @Override
                    public boolean isSpectator() { 
                        return false; 
                    }
                    
                    @Override
                    public boolean isCreative() { 
                        return false; 
                    }
                };
                
                System.out.println("Preview player created successfully!");
            } catch (Exception e) {
                System.err.println("Failed to create preview player: " + e.getMessage());
                this.previewPlayer = null;
            }
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw background
        context.fill(0, 0, this.width, this.height, backgroundColor);
        
        // Draw border
        context.fill(0, 0, this.width, 5, borderColor);
        context.fill(0, this.height - 5, this.width, this.height, borderColor);
        context.fill(0, 0, 5, this.height, borderColor);
        context.fill(this.width - 5, 0, this.width, this.height, borderColor);
        
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("SKIN PREVIEW"), 
            this.width / 2, 20, accentColor);
        
        // Draw preview area
        drawPreviewArea(context);
        
        // Draw skin list
        drawSkinList(context);
        
        // Draw back button hint
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("ESC to go back"), 
            this.width / 2, this.height - 20, 0x888888);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawPreviewArea(DrawContext context) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // Draw preview box
        context.fill(centerX - 100, centerY - 100, centerX + 100, centerY + 100, 0x44000000);
        context.drawBorder(centerX - 100, centerY - 100, 200, 200, borderColor);
        
        // Draw preview text
        if (previewPlayer != null) {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("3D Preview"), 
                centerX, centerY - 80, 0xFFFFFF);
            
            if (selectedSkin != null) {
                context.drawCenteredTextWithShadow(this.textRenderer, 
                    Text.literal("Selected: " + selectedSkin.getName()), 
                    centerX, centerY + 80, accentColor);
            } else {
                context.drawCenteredTextWithShadow(this.textRenderer, 
                    Text.literal("Click a skin to preview"), 
                    centerX, centerY + 80, 0x888888);
            }
        } else {
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("Preview unavailable"), 
                centerX, centerY, 0xFF5555);
        }
    }
    
    private void drawSkinList(DrawContext context) {
        int listX = 50;
        int listY = 80 + scrollOffset;
        int itemHeight = 30;
        
        // Draw list background
        context.fill(30, 60, 220, this.height - 60, 0x44000000);
        
        // Draw title
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("Available Skins (" + skins.size() + ")"), 
            40, 65, accentColor);
        
        for (int i = 0; i < skins.size(); i++) {
            SkinManager.SkinEntry skin = skins.get(i);
            int y = listY + (i * itemHeight);
            
            // Skip if out of bounds
            if (y < 60 || y > this.height - 80) continue;
            
            // Draw skin item background
            int itemColor = (skin == selectedSkin) ? 0x66FFFFFF : 0x33000000;
            context.fill(40, y, 210, y + itemHeight - 2, itemColor);
            
            // Draw skin name
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal("• " + skin.getName()), 
                45, y + 8, 0xFFFFFF);
            
            // Check for click
            if (isMouseOver(mouseX, mouseY, 40, y, 170, itemHeight - 2) && clicked) {
                selectedSkin = skin;
                clicked = false;
            }
        }
    }
    
    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clicked = true;
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scrollOffset += verticalAmount * 20;
        
        // Limit scroll
        int maxScroll = Math.max(0, skins.size() * 30 - (this.height - 150));
        scrollOffset = Math.max(-maxScroll, Math.min(0, scrollOffset));
        
        return true;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC key
            client.setScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
