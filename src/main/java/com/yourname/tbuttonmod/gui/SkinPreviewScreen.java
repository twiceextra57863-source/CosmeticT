package com.yourname.tbuttonmod.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
    
    protected SkinPreviewScreen(Screen parent) {
        super(Text.literal("Skin Preview"));
        this.parent = parent;
        this.skins = SkinManager.getSkins();
        
        // Fix: Proper PlayerEntity creation for 1.21
        World world = MinecraftClient.getInstance().world;
        if (world != null) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "Steve");
            this.previewPlayer = new PlayerEntity(world, null, profile) {
                @Override
                public boolean isSpectator() { 
                    return false; 
                }
                
                @Override
                public boolean isCreative() { 
package com.yourname.tbuttonmod.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
    
    protected SkinPreviewScreen(Screen parent) {
        super(Text.literal("Skin Preview"));
        this.parent = parent;
        this.skins = SkinManager.getSkins();
        
        // FIXED: Proper PlayerEntity creation for Minecraft 1.21
        createPreviewPlayer();
    }
    
    private void createPreviewPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        World world = client.world;
        
        if (world != null) {
            // For Minecraft 1.21, we need to use a different approach
            // Create a dummy player using the client's player data
            if (client.player != null) {
                // Clone the client player for preview
                this.previewPlayer = new PlayerEntity(world, client.player.getBlockPos(), 
                    client.player.getYaw(), new GameProfile(UUID.randomUUID(), "Preview")) {
                    @Override
                    public boolean isSpectator() { 
                        return false; 
                    }
                    
                    @Override
                    public boolean isCreative() { 
                        return false; 
                    }
                };
            } else {
                // Fallback: create a completely new player
                GameProfile profile = new GameProfile(UUID.randomUUID(), "Steve");
                this.previewPlayer = new PlayerEntity(world, null, profile) {
                    @Override
                    public boolean isSpectator() { 
                        return false; 
                    }
                    
                    @Override
                    public boolean isCreative() { 
                        return false; 
                    }
                };
            }
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        
        if (previewPlayer != null) {
            drawPlayerPreview(context);
        }
        
        drawSkinThumbnails(context);
        
        // Draw instructions
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("Click on a skin to preview"), 
            this.width / 2 - 80, this.height - 30, 0xFFFFFF);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawPlayerPreview(DrawContext context) {
        int x = this.width / 2;
        int y = this.height / 2 + 30;
        int size = 80;
        
        try {
            MatrixStack matrices = context.getMatrices();
            matrices.push();
            matrices.translate(x, y, 50);
            matrices.scale(30, 30, 30);
            matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(180)); // Face forward
            
            DiffuseLighting.enableGuiDepthLighting();
            
            MinecraftClient client = MinecraftClient.getInstance();
            EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
            
            dispatcher.setRenderShadows(false);
            dispatcher.render(previewPlayer, 0, 0, 0, 0, 1, matrices, context.getVertexConsumers(), 15728880);
            dispatcher.setRenderShadows(true);
            
            DiffuseLighting.disableGuiDepthLighting();
            matrices.pop();
        } catch (Exception e) {
            // Fallback if rendering fails
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal("Preview unavailable"), 
                x, y, 0xFF5555);
        }
    }
    
    private void drawSkinThumbnails(DrawContext context) {
        int startY = 50 + scrollOffset;
        int thumbSize = 40;
        int thumbX = this.width - 120;
        
        // Draw section title
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("Available Skins (" + skins.size() + ")"), 
            thumbX, 30, accentColor);
        
        for (int i = 0; i < skins.size(); i++) {
            SkinManager.SkinEntry skin = skins.get(i);
            int y = startY + (i * (thumbSize + 10));
            
            // Skip if out of screen bounds
            if (y < 40 || y > this.height - 40) continue;
            
            // Draw thumbnail background
            context.fill(thumbX, y, thumbX + thumbSize, y + thumbSize, 0x88000000);
            context.drawBorder(thumbX, y, thumbSize, thumbSize, borderColor);
            
            // Draw skin name (shortened)
            String shortName = skin.getName().length() > 12 ? 
                skin.getName().substring(0, 10) + "..." : 
                skin.getName();
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal(shortName), 
                thumbX - 70, y + thumbSize/2 - 4, 0xFFFFFF);
            
            // Highlight selected skin
            if (skin == selectedSkin) {
                context.drawBorder(thumbX - 2, y - 2, thumbSize + 4, thumbSize + 4, 0xFFFFFF00);
            }
            
            // Check if clicked
            if (isMouseOver(mouseX, mouseY, thumbX, y, thumbSize, thumbSize) && clicked) {
                selectedSkin = skin;
                clicked = false;
                
                // Here you would apply the skin to the preview player
                // This requires additional implementation
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
        int maxScroll = Math.max(0, skins.size() * 50 - (this.height - 100));
        scrollOffset = Math.min(0, Math.max(-maxScroll, scrollOffset));
        return true;
    }
    
    // Color constants
    private int borderColor = 0xFF6B4E71;
    private int accentColor = 0xFF9B7EBD;
}
