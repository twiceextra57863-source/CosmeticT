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
                    return false; 
                }
            };
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
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawPlayerPreview(DrawContext context) {
        int x = this.width / 2;
        int y = this.height / 2 + 50;
        
        // Setup 3D rendering for player
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(x, y, 50);
        matrices.scale(30, 30, 30);
        
        DiffuseLighting.enableGuiDepthLighting();
        
        // Fix: Use EntityRenderDispatcher to render player
        MinecraftClient client = MinecraftClient.getInstance();
        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        
        if (selectedSkin != null) {
            // Apply skin texture (different approach for 1.21)
            // You may need to use a different method to apply custom skins
        }
        
        dispatcher.setRenderShadows(false);
        dispatcher.render(previewPlayer, 0, 0, 0, 0, 1, matrices, context.getVertexConsumers(), 15728880);
        dispatcher.setRenderShadows(true);
        
        DiffuseLighting.disableGuiDepthLighting();
        matrices.pop();
    }
    
    private void drawSkinThumbnails(DrawContext context) {
        int startY = 50 + scrollOffset;
        int thumbSize = 40;
        
        for (int i = 0; i < skins.size(); i++) {
            SkinManager.SkinEntry skin = skins.get(i);
            int y = startY + (i * (thumbSize + 10));
            
            // Draw thumbnail background
            context.fill(this.width - 100, y, 
                this.width - 40, y + thumbSize, 
                0x88FFFFFF);
            
            // Draw skin name
            context.drawText(this.textRenderer, 
                Text.literal(skin.getName().substring(0, Math.min(10, skin.getName().length()))), 
                this.width - 150, y + thumbSize/2 - 4, 
                0xFFFFFFFF, true);
            
            // Highlight selected skin
            if (skin == selectedSkin) {
                context.drawBorder(this.width - 100, y, thumbSize, thumbSize, 0xFFFFFF00);
            }
            
            // Check if clicked
            if (isMouseOver(mouseX, mouseY, this.width - 100, y, thumbSize, thumbSize) && clicked) {
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
}
