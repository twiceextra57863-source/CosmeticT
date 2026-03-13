package com.yourname.tbuttonmod.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import com.yourname.tbuttonmod.gui.widgets.ModernButton;

public class DashboardScreen extends Screen {
    private final Screen parent;
    private int backgroundColor = 0xDD1A1A2E;
    private int borderColor = 0xFF6B4E71;
    private int accentColor = 0xFF9B7EBD;
    private int currentTheme = 0;
    
    public DashboardScreen(Screen parent) {
        super(Text.literal("T-Cosmetics Dashboard"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Settings button (gear)
        this.addDrawableChild(new ModernButton(
            this.width - 40, 10, 30, 30,
            Text.literal("⚙"),
            button -> this.openSettings()
        ));
        
        // Menu button (3 lines)
        this.addDrawableChild(new ModernButton(
            10, 10, 30, 30,
            Text.literal("☰"),
            button -> this.openMenu()
        ));
    }
    
    private void openSettings() {
        client.setScreen(new ThemeSettingsScreen(this, 
            (theme, bgColor, brColor, accColor) -> {
                this.currentTheme = theme;
                this.backgroundColor = bgColor;
                this.borderColor = brColor;
                this.accentColor = accColor;
            }, currentTheme));
    }
    
    private void openMenu() {
        client.setScreen(new MenuScreen(this));
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
        drawCustomText(context, "T-COSMETICS DASHBOARD", this.width / 2 - 120, 30, accentColor);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawCustomText(DrawContext context, String text, int x, int y, int color) {
        context.drawText(this.textRenderer, text, x + 1, y + 1, 0x88000000, false);
        context.drawText(this.textRenderer, text, x, y, color, false);
    }
}
