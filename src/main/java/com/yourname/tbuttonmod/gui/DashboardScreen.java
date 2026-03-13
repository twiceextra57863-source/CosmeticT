package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;

// 🔴 YEH SAB IMPORTS CHAHIYE
import java.util.Arrays;
import java.util.List;

import com.yourname.tbuttonmod.gui.widgets.ModernButton;
import com.yourname.tbuttonmod.skin.SkinManager;

public class DashboardScreen extends Screen {
    private final Screen parent;
    private int backgroundColor = 0xDD1A1A2E;
    private int borderColor = 0xFF6B4E71;
    private int accentColor = 0xFF9B7EBD;
    
    private List<String> themes = Arrays.asList("Dark Purple", "Ocean Blue", "Forest Green", "Midnight Black");
    private int currentTheme = 0;
    
    public DashboardScreen(Screen parent) {
        super(Text.literal("T-Cosmetics Dashboard"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Settings button (gear)
        // Add your buttons here
    }
    
    private void openSettings() {
        // Open settings
    }
    
    private void openMenu() {
        // Open menu
        MinecraftClient.getInstance().setScreen(new MenuScreen(this));
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Draw custom background
        context.fill(0, 0, this.width, this.height, backgroundColor);
        
        // Draw border effects
        context.fill(0, 0, this.width, 5, borderColor);
        context.fill(0, this.height - 5, this.width, this.height, borderColor);
        context.fill(0, 0, 5, this.height, borderColor);
        context.fill(this.width - 5, 0, this.width, this.height, borderColor);
        
        // Draw title
        drawCustomText(context, "T-COSMETICS", this.width / 2 - 100, 30, accentColor);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawCustomText(DrawContext context, String text, int x, int y, int color) {
        context.drawText(this.textRenderer, text, x + 1, y + 1, 0x88000000, false);
        context.drawText(this.textRenderer, text, x, y, color, false);
    }
}
