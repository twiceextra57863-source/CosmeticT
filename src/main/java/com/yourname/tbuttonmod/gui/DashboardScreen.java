package com.yourname.tbuttonmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("DashboardScreen");
    private final Screen parent;
    
    // Colors
    private int backgroundColor = 0xDD1A1A2E;  // Dark purple
    private int borderColor = 0xFF6B4E71;      // Purple
    private int accentColor = 0xFF9B7EBD;       // Light purple
    
    protected DashboardScreen(Screen parent) {
        super(Text.literal("T-Cosmetics Dashboard"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2 - 100;
        int centerY = this.height / 2 - 60;
        
        LOGGER.info("Initializing dashboard with width: {}, height: {}", this.width, this.height);
        
        try {
            // Settings button (Gear)
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("⚙"),
                button -> {
                    LOGGER.info("Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this));
                }
            ).dimensions(this.width - 50, 10, 40, 20).build());
            
            // Menu button (Three lines)
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("☰"),
                button -> {
                    LOGGER.info("Menu button clicked");
                    this.client.setScreen(new MenuScreen(this));
                }
            ).dimensions(10, 10, 40, 20).build());
            
            // Skin Preview Button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("👤 Skin Preview"),
                button -> {
                    LOGGER.info("Skin Preview button clicked");
                    this.client.setScreen(new SkinPreviewScreen(this));
                }
            ).dimensions(centerX, centerY, 200, 20).build());
            
            // Theme Settings Button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("🎨 Theme Settings"),
                button -> {
                    LOGGER.info("Theme Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this));
                }
            ).dimensions(centerX, centerY + 30, 200, 20).build());
            
            // Back Button
            this.addDrawableChild(ButtonWidget.builder(
                Text.literal("← Back to Main Menu"),
                button -> {
                    LOGGER.info("Back button clicked");
                    this.client.setScreen(parent);
                }
            ).dimensions(centerX, this.height - 40, 200, 20).build());
            
            LOGGER.info("Dashboard initialized with {} buttons", this.children().size());
        } catch (Exception e) {
            LOGGER.error("Error initializing dashboard", e);
        }
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
        
        // Draw title with shadow
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lT-COSMETICS DASHBOARD"), 
            this.width / 2, 30, accentColor);
        
        // Draw version
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("v1.0.0"), 
            this.width - 50, this.height - 20, 0x888888);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
