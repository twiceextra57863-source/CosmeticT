package com.yourname.tbuttonmod.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class DashboardScreen extends Screen {
    private static final Logger LOGGER = LoggerFactory.getLogger("DashboardScreen");
    private final Screen parent;
    
    // Theme colors
    private int backgroundColor = 0xDD1A1A2E;  // Dark purple
    private int borderColor = 0xFF6B4E71;      // Purple
    private int accentColor = 0xFF9B7EBD;       // Light purple
    private int textColor = 0xFFFFFFFF;
    private int buttonColor = 0xFF4A3B4E;
    private int buttonHoverColor = 0xFF6B4E71;
    
    // Current theme
    private String currentTheme = "Dark Purple";
    
    // Stats
    private int skinCount = 0;
    
    public DashboardScreen(TitleScreen parent) {
        super(Text.literal("T-Cosmetics Dashboard"));
        this.parent = parent;
        LOGGER.info("DashboardScreen created");
        updateSkinCount();
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
            this.addDrawableChild(createButton(
                this.width - 45, 5, 35, 20,
                Text.literal("⚙"),
                button -> {
                    LOGGER.info("Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this, this::applyTheme));
                }
            ));
            
            // Menu button (Three lines) - Top left
            this.addDrawableChild(createButton(
                10, 5, 35, 20,
                Text.literal("☰"),
                button -> {
                    LOGGER.info("Menu button clicked");
                    this.client.setScreen(new MenuScreen(this));
                }
            ));
            
            // Reload button - Top center
            this.addDrawableChild(createButton(
                this.width / 2 - 18, 5, 35, 20,
                Text.literal("🔄"),
                button -> {
                    LOGGER.info("Reload button clicked");
                    com.yourname.tbuttonmod.skin.SkinManager.loadSkins();
                    updateSkinCount();
                }
            ));
            
            // ========== MAIN MENU BUTTONS ==========
            
            // Skin Preview Button
            this.addDrawableChild(createButton(
                centerX, centerY, 200, 20,
                Text.literal("👤 Skin Preview"),
                button -> {
                    LOGGER.info("Skin Preview button clicked");
                    this.client.setScreen(new SkinPreviewScreen(this));
                }
            ));
            
            // Theme Settings Button
            this.addDrawableChild(createButton(
                centerX, centerY + 30, 200, 20,
                Text.literal("🎨 Theme Settings"),
                button -> {
                    LOGGER.info("Theme Settings button clicked");
                    this.client.setScreen(new ThemeSettingsScreen(this, this::applyTheme));
                }
            ));
            
            // Open Skins Folder Button
            this.addDrawableChild(createButton(
                centerX, centerY + 60, 200, 20,
                Text.literal("📁 Open Skins Folder"),
                button -> {
                    LOGGER.info("Open Skins Folder button clicked");
                    openSkinsFolder();
                }
            ));
            
            // Reload Skins Button
            this.addDrawableChild(createButton(
                centerX, centerY + 90, 200, 20,
                Text.literal("🔄 Reload Skins"),
                button -> {
                    LOGGER.info("Reload Skins button clicked");
                    com.yourname.tbuttonmod.skin.SkinManager.loadSkins();
                    updateSkinCount();
                }
            ));
            
            // ========== BOTTOM BUTTONS ==========
            
            // Back to Main Menu Button
            this.addDrawableChild(createButton(
                centerX, this.height - 40, 200, 20,
                Text.literal("← Back to Main Menu"),
                button -> {
                    LOGGER.info("Back button clicked");
                    this.client.setScreen(parent);
                }
            ));
            
            // Quit Game Button
            this.addDrawableChild(createButton(
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
    
    private ButtonWidget createButton(int x, int y, int width, int height, 
                                      Text message, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(message, onPress)
            .dimensions(x, y, width, height)
            .build();
    }
    
    private void openSkinsFolder() {
        try {
            File skinsFolder = new File(this.client.runDirectory, "TCosmetics/skins");
            if (!skinsFolder.exists()) {
                skinsFolder.mkdirs();
            }
            
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("explorer " + skinsFolder.getAbsolutePath());
            } else if (os.contains("mac")) {
                // MacOS
                Runtime.getRuntime().exec("open " + skinsFolder.getAbsolutePath());
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux
                Runtime.getRuntime().exec("xdg-open " + skinsFolder.getAbsolutePath());
            } else {
                // Android or other
                LOGGER.info("Skins folder: {}", skinsFolder.getAbsolutePath());
            }
        } catch (Exception e) {
            LOGGER.error("Failed to open skins folder", e);
        }
    }
    
    private void updateSkinCount() {
        File skinsFolder = new File(this.client.runDirectory, "TCosmetics/skins");
        if (skinsFolder.exists()) {
            File[] files = skinsFolder.listFiles((dir, name) -> 
                name.endsWith(".png") || name.endsWith(".jpg"));
            skinCount = files != null ? files.length : 0;
        }
    }
    
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
        // Draw background
        context.fill(0, 0, this.width, this.height, backgroundColor);
        
        // Draw top bar
        context.fill(0, 0, this.width, 30, 0xAA000000);
        context.fill(0, 30, this.width, 32, borderColor);
        
        // Draw bottom bar
        context.fill(0, this.height - 35, this.width, this.height, 0xAA000000);
        context.fill(0, this.height - 37, this.width, this.height - 35, borderColor);
        
        // Draw borders
        context.fill(0, 0, 2, this.height, borderColor);
        context.fill(this.width - 2, 0, this.width, this.height, borderColor);
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§lT-COSMETICS DASHBOARD"), 
            this.width / 2, 12, accentColor);
        
        // Draw theme info
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("Theme: " + currentTheme), 
            15, this.height - 25, 0x888888);
        
        // Draw skin count
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("Skins: " + skinCount), 
            this.width - 150, this.height - 25, 0x888888);
        
        // Draw version
        context.drawTextWithShadow(this.textRenderer, 
            Text.literal("v1.0.0"), 
            this.width / 2 - 20, this.height - 25, 0x444444);
        
        // Draw decorative corners
        drawCornerDecoration(context, 5, 5, true);
        drawCornerDecoration(context, this.width - 25, 5, false);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void drawCornerDecoration(DrawContext context, int x, int y, boolean left) {
        int size = 20;
        if (left) {
            context.fill(x, y, x + size, y + 2, accentColor);
            context.fill(x, y, x + 2, y + size, accentColor);
        } else {
            context.fill(x - size, y, x, y + 2, accentColor);
            context.fill(x - 2, y, x, y + size, accentColor);
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
        // F5 to reload
        if (keyCode == 293) { // F5
            com.yourname.tbuttonmod.skin.SkinManager.loadSkins();
            updateSkinCount();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
