package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("DashboardScreen");
    private final Screen parent;
    
    // Theme colors (can be changed later)
    private int backgroundColor = 0xDD1A1A2E;  // Dark purple background
    private int borderColor = 0xFF6B4E71;      // Purple border
    private int accentColor = 0xFF9B7EBD;       // Light purple accent
    private int textColor = 0xFFFFFFFF;          // White text
    private int buttonColor = 0xFF4A3B4E;        // Dark purple button
    private int buttonHoverColor = 0xFF6B4E71;   // Purple hover
    
    // Current theme name
    private String currentTheme = "Dark Purple";
    
    public DashboardScreen(TitleScreen parent) {
        super(Text.literal("T-Cosmetics Dashboard"));
        this.parent = parent;
        LOGGER.info("DashboardScreen created");
    }
    
    @Override
    protected void init() {
        super.init();
        LOGGER.info("Initializing DashboardScreen with dimensions: {}x{}", this.width, this.height);
        
        int centerX = this.width / 2 - 100;
        int centerY = this.height / 2 - 80;
        
        try {
            // ========== TOP BAR BUTTONS ==========
            
            // Settings button (Gear) - Top right
            this.addDrawableChild(createStyledButton(
                this.width - 45, 5, 35, 20,
                Text.literal("⚙"),
                button -> {
                    LOGGER.info("Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this, this::applyTheme));
                }
            ));
            
            // Menu button (Three lines) - Top left
            this.addDrawableChild(createStyledButton(
                10, 5, 35, 20,
                Text.literal("☰"),
                button -> {
                    LOGGER.info("Menu button clicked");
                    this.client.setScreen(new MenuScreen(this));
                }
            ));
            
            // ========== MAIN MENU BUTTONS ==========
            
            // Skin Preview Button
            this.addDrawableChild(createStyledButton(
                centerX, centerY, 200, 20,
                Text.literal("👤 Skin Preview"),
                button -> {
                    LOGGER.info("Skin Preview button clicked");
                    this.client.setScreen(new SkinPreviewScreen(this));
                }
            ));
            
            // Theme Settings Button
            this.addDrawableChild(createStyledButton(
                centerX, centerY + 30, 200, 20,
                Text.literal("🎨 Theme Settings"),
                button -> {
                    LOGGER.info("Theme Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this, this::applyTheme));
                }
            ));
            
            // Open Skins Folder Button
            this.addDrawableChild(createStyledButton(
                centerX, centerY + 60, 200, 20,
                Text.literal("📁 Open Skins Folder"),
                button -> {
                    LOGGER.info("Open Skins Folder button clicked");
                    openSkinsFolder();
                }
            ));
            
            // Reload Skins Button
            this.addDrawableChild(createStyledButton(
                centerX, centerY + 90, 200, 20,
                Text.literal("🔄 Reload Skins"),
                button -> {
                    LOGGER.info("Reload Skins button clicked");
                    com.yourname.tbuttonmod.skin.SkinManager.loadSkins();
                }
            ));
            
            // ========== BOTTOM BUTTONS ==========
            
            // Back to Main Menu Button
            this.addDrawableChild(createStyledButton(
                centerX, this.height - 40, 200, 20,
                Text.literal("← Back to Main Menu"),
                button -> {
                    LOGGER.info("Back button clicked");
                    this.client.setScreen(parent);
                }
            ));
            
            // Quit Game Button (Small, bottom right)
            this.addDrawableChild(createStyledButton(
                this.width - 110, this.height - 30, 100, 20,
                Text.literal("⏻ Quit"),
                button -> {
                    LOGGER.info("Quit button clicked");
                    this.client.scheduleStop();
                }
            ));
            
            LOGGER.info("DashboardScreen initialized with {} buttons", this.children().size());
            
        } catch (Exception e) {
            LOGGER.error("Error initializing DashboardScreen", e);
        }
    }
    
    /**
     * Creates a styled button with consistent theming
     */
    private ButtonWidget createStyledButton(int x, int y, int width, int height, 
                                           Text message, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(message, onPress)
            .dimensions(x, y, width, height)
            .build();
    }
    
    /**
     * Opens the skins folder in file explorer
     */
    private void openSkinsFolder() {
        try {
            java.io.File skinsFolder = new java.io.File(
                this.client.runDirectory, 
                "TCosmetics/skins"
            );
            
            if (!skinsFolder.exists()) {
                skinsFolder.mkdirs();
            }
            
            // Try to open with desktop (works on PC)
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(skinsFolder);
            } else {
                // Fallback for mobile/other platforms
                LOGGER.info("Skins folder location: {}", skinsFolder.getAbsolutePath());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open skins folder", e);
        }
    }
    
    /**
     * Apply theme colors (called from ThemeSettingsScreen)
     */
    public void applyTheme(int themeIndex, int bgColor, int brColor, int accColor) {
        this.backgroundColor = bgColor;
        this.borderColor = brColor;
        this.accentColor = accColor;
        
        String[] themeNames = {"Dark Purple", "Ocean Blue", "Forest Green", "Midnight Black"};
        if (themeIndex >= 0 && themeIndex < themeNames.length) {
            this.currentTheme = themeNames[themeIndex];
        }
        
        LOGGER.info("Applied theme: {}", currentTheme);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        try {
            // Draw custom gradient background
            renderBackground(context);
            
            // Draw top and bottom bars
            context.fill(0, 0, this.width, 30, 0xAA000000); // Top bar
            context.fill(0, this.height - 35, this.width, this.height, 0xAA000000); // Bottom bar
            
            // Draw decorative borders
            context.fill(0, 30, this.width, 32, borderColor); // Top border line
            context.fill(0, this.height - 37, this.width, this.height - 35, borderColor); // Bottom border line
            
            // Draw left and right borders
            context.fill(0, 0, 2, this.height, borderColor);
            context.fill(this.width - 2, 0, this.width, this.height, borderColor);
            
            // ========== DRAW TITLE ==========
            String title = "§lT-COSMETICS DASHBOARD";
            int titleWidth = this.textRenderer.getWidth(title);
            context.drawCenteredTextWithShadow(this.textRenderer, 
                Text.literal(title), 
                this.width / 2, 12, accentColor);
            
            // ========== DRAW CURRENT THEME ==========
            String themeText = "Theme: " + currentTheme;
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal(themeText), 
                15, this.height - 25, 0x888888);
            
            // ========== DRAW STATISTICS ==========
            int skinCount = com.yourname.tbuttonmod.skin.SkinManager.getSkins().size();
            String statsText = "Skins: " + skinCount;
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal(statsText), 
                this.width - 150, this.height - 25, 0x888888);
            
            // ========== DRAW VERSION ==========
            String versionText = "v1.0.0";
            context.drawTextWithShadow(this.textRenderer, 
                Text.literal(versionText), 
                this.width / 2 - 20, this.height - 25, 0x444444);
            
            // ========== DRAW DECORATIVE ELEMENTS ==========
            // Draw corner decorations
            drawCornerDecoration(context, 5, 5, true);   // Top-left
            drawCornerDecoration(context, this.width - 25, 5, false); // Top-right
            
        } catch (Exception e) {
            LOGGER.error("Error in render", e);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    /**
     * Draw decorative corner elements
     */
    private void drawCornerDecoration(DrawContext context, int x, int y, boolean left) {
        int size = 20;
        if (left) {
            context.fill(x, y, x + size, y + 2, accentColor); // Top line
            context.fill(x, y, x + 2, y + size, accentColor); // Left line
        } else {
            context.fill(x - size, y, x, y + 2, accentColor); // Top line
            context.fill(x - 2, y, x, y + size, accentColor); // Right line
        }
    }
    
    /**
     * Custom background rendering
     */
    private void renderBackground(DrawContext context) {
        // Fill with base color
        context.fill(0, 0, this.width, this.height, backgroundColor);
        
        // Add subtle gradient effect
        for (int i = 0; i < this.height; i += 2) {
            float alpha = 0.05f * (1 - (float)i / this.height);
            int color = ((int)(alpha * 255) << 24) | 0xFFFFFF;
            context.fill(0, i, this.width, i + 1, color);
        }
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public void close() {
        LOGGER.info("Closing DashboardScreen, returning to main menu");
        this.client.setScreen(parent);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // F5 key to reload skins
        if (keyCode == 293) { // F5
            com.yourname.tbuttonmod.skin.SkinManager.loadSkins();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
